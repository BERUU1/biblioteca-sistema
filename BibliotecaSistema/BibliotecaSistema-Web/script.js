// ============================================
// DADOS EM MEMÓRIA (Simulando Backend)
// ============================================
let livros = [];
let usuarios = [];
let emprestimos = [];
let nextLivroId = 1;
let nextUsuarioId = 1;
let nextEmprestimoId = 1;

// ============================================
// NAVEGAÇÃO ENTRE PÁGINAS
// ============================================
function showPage(pageName) {
    const pages = document.querySelectorAll('.page');
    pages.forEach(page => page.style.display = 'none');
    
    document.getElementById(pageName + '-page').style.display = 'block';
    
    // Atualizar link ativo
    document.querySelectorAll('.nav-links a').forEach(link => {
        link.classList.remove('active');
    });
    event.target.classList.add('active');

    // Atualizar dados conforme a página
    if (pageName === 'dashboard') {
        atualizarDashboard();
    } else if (pageName === 'emprestimos') {
        carregarSelectLivros();
        carregarSelectUsuarios();
    }
}

// ============================================
// LIVROS - FUNÇÕES
// ============================================
function cadastrarLivro(event) {
    event.preventDefault();
    
    const titulo = document.getElementById('livro-titulo').value.trim();
    const autor = document.getElementById('livro-autor').value.trim();
    const isbn = document.getElementById('livro-isbn').value.trim();
    
    // Validações
    if (!validarLivro(titulo, autor, isbn)) {
        return;
    }
    
    // Verificar ISBN duplicado
    if (livros.some(l => l.isbn === isbn)) {
        mostrarAlerta('alert-livros', 'ISBN já cadastrado no sistema', 'danger');
        return;
    }
    
    // Adicionar livro
    const novoLivro = {
        id: nextLivroId++,
        titulo,
        autor,
        isbn,
        disponivel: true
    };
    
    livros.push(novoLivro);
    renderizarLivros();
    document.getElementById('form-livro').reset();
    mostrarAlerta('alert-livros', 'Livro cadastrado com sucesso!', 'success');
    atualizarDashboard();
}

function validarLivro(titulo, autor, isbn) {
    let valido = true;
    
    if (titulo.length < 3) {
        document.getElementById('error-titulo').textContent = 'Título deve ter no mínimo 3 caracteres';
        document.getElementById('error-titulo').classList.add('show');
        document.getElementById('livro-titulo').classList.add('error');
        valido = false;
    } else {
        document.getElementById('error-titulo').classList.remove('show');
        document.getElementById('livro-titulo').classList.remove('error');
    }
    
    if (autor.length < 3) {
        document.getElementById('error-autor').textContent = 'Autor deve ter no mínimo 3 caracteres';
        document.getElementById('error-autor').classList.add('show');
        document.getElementById('livro-autor').classList.add('error');
        valido = false;
    } else {
        document.getElementById('error-autor').classList.remove('show');
        document.getElementById('livro-autor').classList.remove('error');
    }
    
    if (isbn.length < 10) {
        document.getElementById('error-isbn').textContent = 'ISBN inválido';
        document.getElementById('error-isbn').classList.add('show');
        document.getElementById('livro-isbn').classList.add('error');
        valido = false;
    } else {
        document.getElementById('error-isbn').classList.remove('show');
        document.getElementById('livro-isbn').classList.remove('error');
    }
    
    return valido;
}

function renderizarLivros() {
    const tbody = document.getElementById('tabela-livros');
    
    if (livros.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem; color: var(--text-secondary);">Nenhum livro cadastrado</td></tr>';
        return;
    }
    
    tbody.innerHTML = livros.map(livro => `
        <tr>
            <td>${livro.id}</td>
            <td>${livro.titulo}</td>
            <td>${livro.autor}</td>
            <td>${livro.isbn}</td>
            <td>
                <span class="badge ${livro.disponivel ? 'badge-success' : 'badge-danger'}">
                    ${livro.disponivel ? 'Disponível' : 'Emprestado'}
                </span>
            </td>
            <td>
                <div class="action-buttons">
                    <button class="btn-icon" onclick="removerLivro(${livro.id})" title="Remover">
                        🗑️
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function removerLivro(id) {
    const livro = livros.find(l => l.id === id);
    
    if (!livro.disponivel) {
        mostrarAlerta('alert-livros', 'Não é possível remover livro emprestado', 'danger');
        return;
    }
    
    if (confirm('Deseja realmente remover este livro?')) {
        livros = livros.filter(l => l.id !== id);
        renderizarLivros();
        mostrarAlerta('alert-livros', 'Livro removido com sucesso!', 'success');
        atualizarDashboard();
    }
}

function buscarLivros() {
    const termo = document.getElementById('search-livros').value.toLowerCase();
    const livrosFiltrados = livros.filter(l => 
        l.titulo.toLowerCase().includes(termo) ||
        l.autor.toLowerCase().includes(termo) ||
        l.isbn.toLowerCase().includes(termo)
    );
    
    const tbody = document.getElementById('tabela-livros');
    
    if (livrosFiltrados.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">Nenhum resultado encontrado</td></tr>';
        return;
    }
    
    tbody.innerHTML = livrosFiltrados.map(livro => `
        <tr>
            <td>${livro.id}</td>
            <td>${livro.titulo}</td>
            <td>${livro.autor}</td>
            <td>${livro.isbn}</td>
            <td>
                <span class="badge ${livro.disponivel ? 'badge-success' : 'badge-danger'}">
                    ${livro.disponivel ? 'Disponível' : 'Emprestado'}
                </span>
            </td>
            <td>
                <button class="btn-icon" onclick="removerLivro(${livro.id})">🗑️</button>
            </td>
        </tr>
    `).join('');
}

// ============================================
// USUÁRIOS - FUNÇÕES
// ============================================
function cadastrarUsuario(event) {
    event.preventDefault();
    
    const nome = document.getElementById('usuario-nome').value.trim();
    const email = document.getElementById('usuario-email').value.trim();
    const cpf = document.getElementById('usuario-cpf').value.trim();
    
    if (!validarUsuario(nome, email, cpf)) {
        return;
    }
    
    if (usuarios.some(u => u.cpf === cpf)) {
        mostrarAlerta('alert-usuarios', 'CPF já cadastrado no sistema', 'danger');
        return;
    }
    
    const novoUsuario = {
        id: nextUsuarioId++,
        nome,
        email,
        cpf
    };
    
    usuarios.push(novoUsuario);
    renderizarUsuarios();
    document.getElementById('form-usuario').reset();
    mostrarAlerta('alert-usuarios', 'Usuário cadastrado com sucesso!', 'success');
    atualizarDashboard();
}

function validarUsuario(nome, email, cpf) {
    let valido = true;
    
    if (nome.length < 3) {
        document.getElementById('error-nome').textContent = 'Nome deve ter no mínimo 3 caracteres';
        document.getElementById('error-nome').classList.add('show');
        valido = false;
    } else {
        document.getElementById('error-nome').classList.remove('show');
    }
    
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        document.getElementById('error-email').textContent = 'E-mail inválido';
        document.getElementById('error-email').classList.add('show');
        valido = false;
    } else {
        document.getElementById('error-email').classList.remove('show');
    }
    
    const cpfLimpo = cpf.replace(/\D/g, '');
    if (cpfLimpo.length !== 11) {
        document.getElementById('error-cpf').textContent = 'CPF deve ter 11 dígitos';
        document.getElementById('error-cpf').classList.add('show');
        valido = false;
    } else {
        document.getElementById('error-cpf').classList.remove('show');
    }
    
    return valido;
}

function renderizarUsuarios() {
    const tbody = document.getElementById('tabela-usuarios');
    
    if (usuarios.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Nenhum usuário cadastrado</td></tr>';
        return;
    }
    
    tbody.innerHTML = usuarios.map(usuario => `
        <tr>
            <td>${usuario.id}</td>
            <td>${usuario.nome}</td>
            <td>${usuario.email}</td>
            <td>${usuario.cpf}</td>
            <td>
                <button class="btn-icon" onclick="removerUsuario(${usuario.id})">🗑️</button>
            </td>
        </tr>
    `).join('');
}

function removerUsuario(id) {
    if (confirm('Deseja realmente remover este usuário?')) {
        usuarios = usuarios.filter(u => u.id !== id);
        renderizarUsuarios();
        mostrarAlerta('alert-usuarios', 'Usuário removido com sucesso!', 'success');
        atualizarDashboard();
    }
}

function buscarUsuarios() {
    const termo = document.getElementById('search-usuarios').value.toLowerCase();
    const usuariosFiltrados = usuarios.filter(u => 
        u.nome.toLowerCase().includes(termo) ||
        u.email.toLowerCase().includes(termo) ||
        u.cpf.includes(termo)
    );
    
    const tbody = document.getElementById('tabela-usuarios');
    
    if (usuariosFiltrados.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem;">Nenhum resultado encontrado</td></tr>';
        return;
    }
    
    tbody.innerHTML = usuariosFiltrados.map(usuario => `
        <tr>
            <td>${usuario.id}</td>
            <td>${usuario.nome}</td>
            <td>${usuario.email}</td>
            <td>${usuario.cpf}</td>
            <td>
                <button class="btn-icon" onclick="removerUsuario(${usuario.id})">🗑️</button>
            </td>
        </tr>
    `).join('');
}

// Máscara de CPF
document.addEventListener('DOMContentLoaded', function() {
    const cpfInput = document.getElementById('usuario-cpf');
    if (cpfInput) {
        cpfInput.addEventListener('input', function(e) {
            let valor = e.target.value.replace(/\D/g, '');
            if (valor.length <= 11) {
                valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
                valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
                valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
                e.target.value = valor;
            }
        });
    }
});

// ============================================
// EMPRÉSTIMOS - FUNÇÕES
// ============================================
function carregarSelectLivros() {
    const select = document.getElementById('emprestimo-livro');
    const livrosDisponiveis = livros.filter(l => l.disponivel);
    
    select.innerHTML = '<option value="">Selecione um livro</option>' +
        livrosDisponiveis.map(l => `<option value="${l.id}">${l.titulo}</option>`).join('');
}

function carregarSelectUsuarios() {
    const select = document.getElementById('emprestimo-usuario');
    
    select.innerHTML = '<option value="">Selecione um usuário</option>' +
        usuarios.map(u => `<option value="${u.id}">${u.nome}</option>`).join('');
}

function realizarEmprestimo(event) {
    event.preventDefault();
    
    const livroId = parseInt(document.getElementById('emprestimo-livro').value);
    const usuarioId = parseInt(document.getElementById('emprestimo-usuario').value);
    
    if (!livroId || !usuarioId) {
        mostrarAlerta('alert-emprestimos', 'Selecione livro e usuário', 'danger');
        return;
    }
    
    const livro = livros.find(l => l.id === livroId);
    const usuario = usuarios.find(u => u.id === usuarioId);
    
    if (!livro.disponivel) {
        mostrarAlerta('alert-emprestimos', 'Livro não está disponível', 'danger');
        return;
    }
    
    const dataEmprestimo = new Date();
    const dataDevolucao = new Date();
    dataDevolucao.setDate(dataDevolucao.getDate() + 14);
    
    const novoEmprestimo = {
        id: nextEmprestimoId++,
        livroId,
        usuarioId,
        livroTitulo: livro.titulo,
        usuarioNome: usuario.nome,
        dataEmprestimo: dataEmprestimo.toISOString().split('T')[0],
        dataDevolucao: dataDevolucao.toISOString().split('T')[0],
        devolvido: false
    };
    
    emprestimos.push(novoEmprestimo);
    livro.disponivel = false;
    
    renderizarEmprestimos();
    carregarSelectLivros();
    document.getElementById('form-emprestimo').reset();
    mostrarAlerta('alert-emprestimos', 'Empréstimo realizado com sucesso!', 'success');
    atualizarDashboard();
}

function renderizarEmprestimos() {
    const tbody = document.getElementById('tabela-emprestimos');
    const emprestimosAtivos = emprestimos.filter(e => !e.devolvido);
    
    if (emprestimosAtivos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" style="text-align: center; padding: 2rem;">Nenhum empréstimo ativo</td></tr>';
        return;
    }
    
    tbody.innerHTML = emprestimosAtivos.map(emp => {
        const hoje = new Date();
        const devolucao = new Date(emp.dataDevolucao);
        const atrasado = hoje > devolucao;
        
        return `
            <tr>
                <td>${emp.id}</td>
                <td>${emp.livroTitulo}</td>
                <td>${emp.usuarioNome}</td>
                <td>${emp.dataEmprestimo}</td>
                <td>${emp.dataDevolucao}</td>
                <td>
                    <span class="badge ${atrasado ? 'badge-danger' : 'badge-warning'}">
                        ${atrasado ? 'Atrasado' : 'Ativo'}
                    </span>
                </td>
                <td>
                    <button class="btn btn-success" onclick="realizarDevolucao(${emp.id})" style="padding: 0.5rem 1rem;">
                        Devolver
                    </button>
                </td>
            </tr>
        `;
    }).join('');
}

function realizarDevolucao(id) {
    const emprestimo = emprestimos.find(e => e.id === id);
    
    if (confirm('Confirmar devolução do livro?')) {
        emprestimo.devolvido = true;
        
        const livro = livros.find(l => l.id === emprestimo.livroId);
        livro.disponivel = true;
        
        renderizarEmprestimos();
        mostrarAlerta('alert-emprestimos', 'Livro devolvido com sucesso!', 'success');
        atualizarDashboard();
    }
}

// ============================================
// DASHBOARD - FUNÇÕES
// ============================================
function atualizarDashboard() {
    document.getElementById('total-livros').textContent = livros.length;
    document.getElementById('livros-disponiveis').textContent = livros.filter(l => l.disponivel).length;
    document.getElementById('emprestimos-ativos').textContent = emprestimos.filter(e => !e.devolvido).length;
    document.getElementById('total-usuarios').textContent = usuarios.length;
    
    renderizarEmprestimosRecentes();
}

function renderizarEmprestimosRecentes() {
    const tbody = document.getElementById('emprestimos-recentes');
    const emprestimosRecentes = emprestimos.slice(-5).reverse();
    
    if (emprestimosRecentes.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem;">Nenhum empréstimo registrado</td></tr>';
        return;
    }
    
    tbody.innerHTML = emprestimosRecentes.map(emp => `
        <tr>
            <td>${emp.id}</td>
            <td>${emp.livroTitulo}</td>
            <td>${emp.usuarioNome}</td>
            <td>${emp.dataEmprestimo}</td>
            <td>${emp.dataDevolucao}</td>
            <td>
                <span class="badge ${emp.devolvido ? 'badge-success' : 'badge-warning'}">
                    ${emp.devolvido ? 'Devolvido' : 'Ativo'}
                </span>
            </td>
        </tr>
    `).join('');
}

// ============================================
// FUNÇÕES AUXILIARES
// ============================================
function mostrarAlerta(id, mensagem, tipo) {
    const alerta = document.getElementById(id);
    alerta.textContent = mensagem;
    alerta.className = `alert alert-${tipo} show`;
    
    setTimeout(() => {
        alerta.classList.remove('show');
    }, 5000);
}

// ============================================
// INICIALIZAÇÃO
// ============================================
window.onload = function() {
    // Dados de exemplo
    livros = [
        { id: nextLivroId++, titulo: 'Clean Code', autor: 'Robert Martin', isbn: '978-0132350884', disponivel: true },
        { id: nextLivroId++, titulo: 'Design Patterns', autor: 'Gang of Four', isbn: '978-0201633612', disponivel: false },
        { id: nextLivroId++, titulo: 'Refactoring', autor: 'Martin Fowler',isbn: '978-0134757599', disponivel: true }
];

usuarios = [
    { id: nextUsuarioId++, nome: 'João Silva', email: 'joao@email.com', cpf: '123.456.789-00' },
    { id: nextUsuarioId++, nome: 'Maria Santos', email: 'maria@email.com', cpf: '987.654.321-00' }
];

emprestimos = [
    {
        id: nextEmprestimoId++,
        livroId: 2,
        usuarioId: 1,
        livroTitulo: 'Design Patterns',
        usuarioNome: 'João Silva',
        dataEmprestimo: '2026-01-01',
        dataDevolucao: '2026-01-15',
        devolvido: false
    }
];

atualizarDashboard();
renderizarLivros();
renderizarUsuarios();
renderizarEmprestimos();
};

