const chaveVeterinario = 'veterinario';

/**
 * Intercepta a submissão do form de login e age de forma customizada.
 * @param {Event} evento O evento que deu origem ao processo de login
 */
async function formSubmitLogin(evento) {

    document.getElementById('btnLogin').setAttribute('disabled', '');

    evento.preventDefault();
    evento.stopPropagation();

    try { 
        let sucesso = await realizaLogin();
        if (!sucesso) { alert('Credenciais inválidas'); }
        else { location.href="/index.html"; }
    }
    finally { document.getElementById('btnLogin').removeAttribute('disabled'); }

    return false;
}

/**
 * Realiza login
 * @param {*} evento O evento de form submit que deu origem ao login.
 */
async function realizaLogin() {

    let validado = false;

	await fetch('http://localhost:6789/login', {
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
		body: JSON.stringify({ email: document.getElementById('inputEmail').value, senha: document.getElementById('inputSenha').value })
	}).then(function (response){
        if (response.status === 200) {
            localStorage.setItem('veterinario', JSON.stringify(response.text()));
            validado = true;
        }
    });
    
    return validado;
}

/**
 * Deloga o usuario
 */
function deslogar() {
    localStorage.removeItem('veterinario');
    location.href="/login.html";
}

/**
 * Verifica se ha um usuario logado
 */
function verificarLogin() {
    return localStorage.getItem('veterinario') !== null;
}

/**
 * Registra um usuário
 * @param {*} evento O evento de form submit que deu origem ao registro.
 */
function registrar(evento) {
    evento.preventDefault();
    evento.stopPropagation();

    let nome = document.getElementById('inputNome').value;
    let email = document.getElementById('inputEmail').value;
    let senha = document.getElementById('inputSenha').value;
    let telefone = document.getElementById('inputTelefone').value;
    let crmv = document.getElementById('inputCrmv').value;

	fetch('http://localhost:6789/veterinarios', {
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
		body: JSON.stringify({ nome: nome, senha: senha, crmv: crmv, telefone: telefone, email: email })
	}).then(function (response){
        if (response.status === 200) {
            localStorage.setItem('veterinario', JSON.stringify(response.text()));
            validado = true;
        }
    });
    
    return false;
}

(() => {
    if (!verificarLogin() && (location.pathname != '/login.html' && location.pathname != '/registro.html')) {
        location.href = "/login.html";
    }
})();