const API = '/api/students';

async function loadStudents() {
  const res = await fetch(API);
  const students = await res.json();
  const tbody = document.getElementById('tbody');
  tbody.innerHTML = '';
  document.getElementById('empty').style.display = students.length ? 'none' : 'block';
  students.forEach(s => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${s.id}</td>
      <td>${esc(s.name)}</td>
      <td>${esc(s.email)}</td>
      <td>${esc(s.course)}</td>
      <td>
        <button onclick="editStudent(${s.id})">Edit</button>
        <button class="red" onclick="deleteStudent(${s.id})">Delete</button>
      </td>`;
    tbody.appendChild(tr);
  });
}

function esc(v) {
  return String(v ?? '').replace(/[&<>"']/g, c =>
    ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
}

// Backend GlobalExceptionHandler anuppura error ah screen la kaatum
function showError(err) {
  const box = document.getElementById('error');
  let text = err.message || 'Something went wrong';
  if (err.fieldErrors) {
    text = Object.values(err.fieldErrors).join(' | ');
  }
  box.innerText = text;
  box.style.display = 'block';
}

function clearError() {
  document.getElementById('error').style.display = 'none';
}

async function saveStudent() {
  clearError();
  const id = document.getElementById('id').value;
  const body = {
    name: document.getElementById('name').value,
    email: document.getElementById('email').value,
    course: document.getElementById('course').value
  };

  const res = await fetch(id ? `${API}/${id}` : API, {
    method: id ? 'PUT' : 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  });

  if (!res.ok) {
    showError(await res.json());
    return;
  }
  resetForm();
  loadStudents();
}

async function editStudent(id) {
  clearError();
  const res = await fetch(`${API}/${id}`);
  if (!res.ok) { showError(await res.json()); loadStudents(); return; }
  const s = await res.json();
  document.getElementById('id').value = s.id;
  document.getElementById('name').value = s.name;
  document.getElementById('email').value = s.email;
  document.getElementById('course').value = s.course;
  document.getElementById('formTitle').innerText = 'Edit Student';
  window.scrollTo({ top: 0, behavior: 'smooth' });
}

async function deleteStudent(id) {
  if (!confirm('Delete panlama?')) return;
  clearError();
  const res = await fetch(`${API}/${id}`, { method: 'DELETE' });
  if (!res.ok) showError(await res.json());
  loadStudents();
}

function resetForm() {
  ['id', 'name', 'email', 'course'].forEach(f => document.getElementById(f).value = '');
  document.getElementById('formTitle').innerText = 'Add Student';
  clearError();
}

loadStudents();
