const API = 'http://localhost:8080/api';

// ─── Auth Guard ────────────────────────────────────────────────────────
const ME = JSON.parse(sessionStorage.getItem('ems_user'));
if (!ME) { window.location.href = 'login.html'; }

const IS_ADMIN = ME && ME.role === 'ADMIN';

// ─── State ──────────────────────────────────────────────────────────────
let allEvents = [], allUsers = [], allRegs = [], pendingRegEventId = null;

// ─── On Load ────────────────────────────────────────────────────────────
window.addEventListener('DOMContentLoaded', () => {
    initUserChip();
    applyRoleRestrictions();
    setupNav();
    checkApiStatus();
    loadDashboard();
});

// ─── User Chip ──────────────────────────────────────────────────────────
function initUserChip() {
    document.getElementById('chip-avatar').textContent = ME.username.charAt(0).toUpperCase();
    document.getElementById('chip-name').textContent = ME.username;
    document.getElementById('chip-role').textContent = ME.role;
}

// ─── Role-based UI ──────────────────────────────────────────────────────
function applyRoleRestrictions() {
    if (!IS_ADMIN) {
        hide('nav-users');
        hide('nav-reports');
        hide('btn-add-event');
        hide('btn-add-user');
    }
}

// ─── Navigation ─────────────────────────────────────────────────────────
function setupNav() {
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', e => {
            e.preventDefault();
            switchView(link.dataset.view);
        });
    });
}

function switchView(viewId) {
    if (!viewId) return;

    document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
    document.querySelectorAll('.view').forEach(v => v.classList.remove('active'));

    const navEl = document.getElementById('nav-' + viewId);
    const viewEl = document.getElementById('view-' + viewId);
    if (navEl) navEl.classList.add('active');
    if (viewEl) viewEl.classList.add('active');

    const titles = {
        dashboard:     ['Dashboard',     'Overview of your event management system'],
        events:        ['Events',         'Browse and manage all events'],
        users:         ['Users',          'Manage platform users and roles'],
        registrations: ['Registrations',  'View all event registrations'],
        reports:       ['Reports',        'Live stats powered by Kafka messaging'],
    };
    const [title, sub] = titles[viewId] || ['', ''];
    document.getElementById('page-title').textContent = title;
    document.getElementById('page-sub').textContent = sub;

    if (viewId === 'dashboard')     loadDashboard();
    if (viewId === 'events')        loadEvents();
    if (viewId === 'users')         loadUsers();
    if (viewId === 'registrations') loadRegistrations();
    if (viewId === 'reports')       loadReports();
}

// ─── API Status ─────────────────────────────────────────────────────────
async function checkApiStatus() {
    const el = document.getElementById('api-status');
    try {
        const r = await fetch(`${API}/events`, { signal: AbortSignal.timeout(3000) });
        if (r.ok) { el.textContent = '🟢 API Online'; el.classList.add('online'); }
        else throw new Error();
    } catch {
        el.textContent = '🔴 API Offline'; el.classList.add('offline');
    }
}

// ─── DASHBOARD ──────────────────────────────────────────────────────────
async function loadDashboard() {
    try {
        const [events, users, regs, reports] = await Promise.all([
            fetchJson(`${API}/events`),
            fetchJson(`${API}/users`),
            fetchJson(`${API}/registrations`),
            fetchJson(`${API}/reports/statistics`),
        ]);

        allEvents = events || [];
        allUsers  = users  || [];
        allRegs   = regs   || [];

        setText('stat-events',  allEvents.length);
        setText('stat-users',   allUsers.length);
        setText('stat-regs',    allRegs.length);
        setText('stat-reports', (reports || []).length);

        renderRecentEvents(allEvents.slice(-5).reverse());
        renderRecentRegs(allRegs.slice(-5).reverse());
    } catch (e) {
        console.error(e);
    }
}

function renderRecentEvents(events) {
    const el = document.getElementById('recent-events-list');
    if (!events.length) { el.innerHTML = emptyState('No events yet'); return; }
    el.innerHTML = events.map(e => `
        <div class="recent-item">
            <div>
                <div class="recent-item-title">${e.title}</div>
                <div class="recent-item-sub">📍 ${e.location}</div>
            </div>
            <span class="meta-tag">${formatDate(e.eventDate)}</span>
        </div>
    `).join('');
}

function renderRecentRegs(regs) {
    const el = document.getElementById('recent-regs-list');
    if (!regs.length) { el.innerHTML = emptyState('No registrations yet'); return; }
    el.innerHTML = regs.map(r => `
        <div class="recent-item">
            <div>
                <div class="recent-item-title">User #${r.userId} → Event #${r.eventId}</div>
                <div class="recent-item-sub">${formatDate(r.registrationDate)}</div>
            </div>
            <span class="badge badge-confirmed">${r.status}</span>
        </div>
    `).join('');
}

// ─── EVENTS ─────────────────────────────────────────────────────────────
async function loadEvents() {
    const grid = document.getElementById('events-grid');
    grid.innerHTML = '<div class="loader"></div>';
    allEvents = await fetchJson(`${API}/events`) || [];
    renderEventCards(allEvents);
}

function renderEventCards(events) {
    const grid = document.getElementById('events-grid');
    if (!events.length) { grid.innerHTML = emptyState('No events found'); return; }
    grid.innerHTML = events.map(e => `
        <div class="event-card">
            <div class="event-card-top">
                <div class="event-card-title">${e.title}</div>
            </div>
            <div class="event-card-desc">${e.description}</div>
            <div class="event-meta">
                <span class="meta-tag">📍 ${e.location}</span>
                <span class="meta-tag">🕒 ${formatDate(e.eventDate)}</span>
                <span class="meta-tag">👤 Organizer #${e.organizerId}</span>
            </div>
            <div class="event-card-actions">
                <button class="btn-sm btn-register" onclick="openRegister(${e.id}, '${e.title}')">✅ Register</button>
                ${IS_ADMIN ? `<button class="btn-sm btn-edit" onclick="editEvent(${e.id})">✏️ Edit</button>` : ''}
                ${IS_ADMIN ? `<button class="btn-sm btn-delete" onclick="deleteEvent(${e.id})">🗑 Delete</button>` : ''}
            </div>
        </div>
    `).join('');
}

function filterEvents() {
    const q = document.getElementById('event-search').value.toLowerCase();
    renderEventCards(allEvents.filter(e =>
        e.title.toLowerCase().includes(q) || e.location.toLowerCase().includes(q)
    ));
}

async function submitEvent(e) {
    e.preventDefault();
    const id = document.getElementById('event-id-hidden').value;
    const body = {
        title:       document.getElementById('e-title').value,
        description: document.getElementById('e-desc').value,
        location:    document.getElementById('e-location').value,
        eventDate:   document.getElementById('e-date').value,
        organizerId: ME.userId,
    };
    const url    = id ? `${API}/events/${id}` : `${API}/events`;
    const method = id ? 'PUT' : 'POST';
    const ok = await sendJson(url, method, body);
    if (ok) { closeModal('modal-event'); toast('Event saved successfully!'); loadEvents(); }
    else toast('Failed to save event', true);
}

async function editEvent(id) {
    const e = allEvents.find(x => x.id === id);
    if (!e) return;
    document.getElementById('modal-event-title').textContent = 'Edit Event';
    document.getElementById('event-id-hidden').value = e.id;
    document.getElementById('e-title').value    = e.title;
    document.getElementById('e-desc').value     = e.description;
    document.getElementById('e-location').value = e.location;
    document.getElementById('e-date').value     = e.eventDate ? e.eventDate.substring(0, 16) : '';
    openModal('modal-event');
}

async function deleteEvent(id) {
    if (!confirm('Delete this event?')) return;
    const r = await fetch(`${API}/events/${id}`, { method: 'DELETE' });
    if (r.ok) { toast('Event deleted'); loadEvents(); }
    else toast('Delete failed', true);
}

// ─── REGISTRATION ───────────────────────────────────────────────────────
function openRegister(eventId, title) {
    pendingRegEventId = eventId;
    document.getElementById('reg-event-info').textContent = `Event: "${title}" (ID: ${eventId})`;
    document.getElementById('reg-user-name').textContent  = `${ME.username} (ID: ${ME.userId})`;
    openModal('modal-register');
}

async function confirmRegister() {
    const body = { eventId: pendingRegEventId, userId: ME.userId };
    const ok = await sendJson(`${API}/registrations`, 'POST', body);
    if (ok) {
        closeModal('modal-register');
        toast('🎉 Registered! Kafka message sent to reporting service.');
    } else {
        toast('Registration failed', true);
    }
}

// ─── USERS ──────────────────────────────────────────────────────────────
async function loadUsers() {
    const tb = document.getElementById('users-tbody');
    tb.innerHTML = '<tr><td colspan="5"><div class="loader"></div></td></tr>';
    allUsers = await fetchJson(`${API}/users`) || [];
    renderUsersTable(allUsers);
}

function renderUsersTable(users) {
    const tb = document.getElementById('users-tbody');
    if (!users.length) { tb.innerHTML = `<tr><td colspan="5">${emptyState('No users found')}</td></tr>`; return; }
    tb.innerHTML = users.map(u => `
        <tr>
            <td>#${u.id}</td>
            <td style="font-weight:600">${u.username}</td>
            <td style="color:var(--muted)">${u.email}</td>
            <td><span class="badge ${u.role === 'ADMIN' ? 'badge-admin' : 'badge-user'}">${u.role}</span></td>
            <td>
                ${IS_ADMIN ? `<div style="display:flex;gap:6px">
                    <button class="btn-sm btn-edit"   onclick="editUser(${u.id})">✏️ Edit</button>
                    <button class="btn-sm btn-delete" onclick="deleteUser(${u.id})">🗑 Delete</button>
                </div>` : '—'}
            </td>
        </tr>
    `).join('');
}

function filterUsers() {
    const q = document.getElementById('user-search').value.toLowerCase();
    renderUsersTable(allUsers.filter(u =>
        u.username.toLowerCase().includes(q) || u.email.toLowerCase().includes(q)
    ));
}

async function submitUser(e) {
    e.preventDefault();
    const id = document.getElementById('user-id-hidden').value;
    const body = {
        username: document.getElementById('u-name').value,
        email:    document.getElementById('u-email').value,
        role:     document.getElementById('u-role').value,
    };
    const url    = id ? `${API}/users/${id}` : `${API}/users`;
    const method = id ? 'PUT' : 'POST';
    const ok = await sendJson(url, method, body);
    if (ok) { closeModal('modal-user'); toast('User saved!'); loadUsers(); }
    else toast('Failed to save user', true);
}

async function editUser(id) {
    const u = allUsers.find(x => x.id === id);
    if (!u) return;
    document.getElementById('modal-user-title').textContent = 'Edit User';
    document.getElementById('user-id-hidden').value = u.id;
    document.getElementById('u-name').value  = u.username;
    document.getElementById('u-email').value = u.email;
    document.getElementById('u-role').value  = u.role;
    openModal('modal-user');
}

async function deleteUser(id) {
    if (!confirm('Delete this user?')) return;
    const r = await fetch(`${API}/users/${id}`, { method: 'DELETE' });
    if (r.ok) { toast('User deleted'); loadUsers(); }
    else toast('Delete failed', true);
}

// ─── REGISTRATIONS ──────────────────────────────────────────────────────
async function loadRegistrations() {
    const tb = document.getElementById('regs-tbody');
    tb.innerHTML = '<tr><td colspan="5"><div class="loader"></div></td></tr>';
    allRegs = await fetchJson(`${API}/registrations`) || [];
    renderRegsTable(allRegs);
}

function renderRegsTable(regs) {
    const tb = document.getElementById('regs-tbody');
    if (!regs.length) { tb.innerHTML = `<tr><td colspan="5">${emptyState('No registrations found')}</td></tr>`; return; }
    tb.innerHTML = regs.map(r => `
        <tr>
            <td>#${r.id}</td>
            <td><span class="meta-tag">Event #${r.eventId}</span></td>
            <td><span class="meta-tag">User #${r.userId}</span></td>
            <td style="color:var(--muted);font-size:0.82rem">${formatDate(r.registrationDate)}</td>
            <td><span class="badge badge-confirmed">${r.status || 'CONFIRMED'}</span></td>
        </tr>
    `).join('');
}

function filterRegs() {
    const q = document.getElementById('reg-search').value.toLowerCase();
    renderRegsTable(allRegs.filter(r =>
        String(r.eventId).includes(q) || String(r.userId).includes(q)
    ));
}

// ─── REPORTS ────────────────────────────────────────────────────────────
async function loadReports() {
    const tb = document.getElementById('reports-tbody');
    tb.innerHTML = '<tr><td colspan="3"><div class="loader"></div></td></tr>';
    const data = await fetchJson(`${API}/reports/statistics`) || [];
    if (!data.length) { tb.innerHTML = `<tr><td colspan="3">${emptyState('No report data yet. Register users for events to generate stats.')}</td></tr>`; return; }
    const max = Math.max(...data.map(d => d.totalRegistrations), 1);
    tb.innerHTML = data.map(d => {
        const pct = Math.round((d.totalRegistrations / max) * 100);
        return `<tr>
            <td style="font-weight:600">Event #${d.eventId}</td>
            <td style="font-size:1.1rem;font-weight:700;color:var(--primary-light)">${d.totalRegistrations}</td>
            <td>
                <div style="display:flex;align-items:center;gap:10px">
                    <div class="progress-bar-wrap" style="flex:1">
                        <div class="progress-bar" style="width:${pct}%"></div>
                    </div>
                    <span style="color:var(--muted);font-size:0.8rem;width:36px;text-align:right">${pct}%</span>
                </div>
            </td>
        </tr>`;
    }).join('');
}

// ─── MODALS ─────────────────────────────────────────────────────────────
function openModal(id) {
    document.getElementById(id).classList.remove('hidden');
}

function closeModal(id) {
    document.getElementById(id).classList.add('hidden');
    // Reset forms
    const form = document.getElementById(id)?.querySelector('form');
    if (form) {
        form.reset();
        const hiddenId = form.querySelector('input[type=hidden]');
        if (hiddenId) hiddenId.value = '';
    }
}

// Close on backdrop click
document.querySelectorAll('.modal-bg').forEach(bg => {
    bg.addEventListener('click', e => { if (e.target === bg) closeModal(bg.id); });
});

// ─── LOGOUT ─────────────────────────────────────────────────────────────
function doLogout() {
    sessionStorage.removeItem('ems_user');
    window.location.href = 'login.html';
}

// ─── HELPERS ────────────────────────────────────────────────────────────
async function fetchJson(url) {
    try {
        const r = await fetch(url);
        if (!r.ok) return null;
        return await r.json();
    } catch { return null; }
}

async function sendJson(url, method, body) {
    try {
        const r = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
        });
        return r.ok;
    } catch { return false; }
}

function formatDate(dt) {
    if (!dt) return '—';
    try { return new Date(dt).toLocaleString('en-IN', { day:'2-digit', month:'short', year:'numeric', hour:'2-digit', minute:'2-digit' }); }
    catch { return dt; }
}

function setText(id, val) {
    const el = document.getElementById(id);
    if (el) el.textContent = val;
}

function hide(id) {
    const el = document.getElementById(id);
    if (el) el.style.display = 'none';
}

function emptyState(msg) {
    return `<div class="empty-state"><p>🗂</p><p>${msg}</p></div>`;
}

let toastTimer;
function toast(msg, isError = false) {
    const el = document.getElementById('toast');
    el.textContent = isError ? '❌ ' + msg : '✅ ' + msg;
    el.className = 'toast' + (isError ? ' error' : '');
    clearTimeout(toastTimer);
    toastTimer = setTimeout(() => el.classList.add('hidden'), 3500);
}
