/**
 * Define as rotas para a aplicação.
 */
const rotas = [
    { hash: '#visualizarClientes', propriedades: { template: '/modulos/cliente/visualizar.html', callback: filtroCliente } },
    { hash: '#cadastrarCliente', propriedades: { template: '/modulos/cliente/cadastrar.html', callback: null } },
    { hash: '#visualizarCliente/', propriedades: { template: '/modulos/cliente/detalhe.html', callback: detalheCliente } },
    { hash: '#cadastrarAnimal/', propriedades: { template: '/modulos/animal/cadastrar.html', callback: null } },
    { hash: '#consultar/', propriedades: { template: '/modulos/animal/consultar.html', callback: null } }
];

/**
 * Realiza o roteamento via hash.
 */
function rotear() {

    if (location.hash == '#' || !location.hash) {
        document.getElementById('main').innerText = '';
        return;
    }

    let carregado = true;
    rotas.forEach((rota) => {
        if (location.hash.startsWith(rota.hash)) {
            console.log(rota);
            console.log(location.hash);

            carregar(rota.propriedades.template, 'main', rota.propriedades.callback);
            carregado = true;
            return;
        }
    });

    if (!carregado) {
        document.getElementById('main').innerText = '';
        alert("Falha no roteamento da aplicação");
    }
}

/**
 * Carrega uma rota
 * @param {string} path O caminho do conteudo a ser carregado
 * @param {string} div O div de destino do conteoudo
 * @param {function} callback A funcao de callback, ou null
 */
function carregar(path, div, callback) {
    fetch(path)
        .then(function (resposta) {
            if (resposta.status === 200) { return resposta.text(); }
            else { return null; }
        }).then(function (conteudo) {
            if (conteudo === '') {
                alert("Falha ao carregar o módulo solicitado.");
            } else {
                document.getElementById(div).innerHTML = conteudo;
                if (callback !== null) { callback(); }
            }
        });
}

/**
 * Carega clientes (callback)
 * @param {*} colecao Os clientes para carregar
 */
function carregarClientes(colecao) {

    let tabela = document.getElementById('tabelaClientes');
    let tbody = tabela.getElementsByTagName('tbody')[0];

    tbody.innerHtml = '';
    tbody.innerText = '';

    colecao.forEach(item => {
        let tr = document.createElement("tr");
        tr.setAttribute('cliente-id', item.id);
        tbody.appendChild(tr);

        let tdNome = document.createElement('td');
        tr.appendChild(tdNome);
        tdNome.innerText = item.nome;

        let tdEmail = document.createElement('td');
        tr.appendChild(tdEmail);
        tdEmail.innerText = item.email;

        let tdTelefone = document.createElement('td');
        tr.appendChild(tdTelefone);
        tdTelefone.innerText = item.telefone;

        let tdAcoes = document.createElement('td');
        tr.appendChild(tdAcoes);
        
        let a = document.createElement("a");
        a.href="#visualizarCliente/" + item.id;
        a.text = "Visualizar";
        tdAcoes.appendChild(a);
    });
}

/**
 * Visualiza detalhe do cliente
 */
function detalheCliente() {
    let id = location.hash.replace('#visualizarCliente/', '');

    fetch('http://localhost:6789/clientes/' + id)
    .then((resposta) => {
        if (resposta.status == 200) { return resposta.json(); }
        else { console.log(resposta.status); return {}; }
    })
    .then((cliente) => {
        carregarComModelo('/modulos/cliente/detalhe.html', 'main', cliente, () => { carregarPets(cliente.id); document.getElementById('linkCadastrarPet').href='#cadastrarAnimal/' + cliente.id; });
    });
}

/**
 * Carrega um template de interface com pós-renderização de dados.
 * @param {string} path O local do template para renderização
 * @param {HTMLDivElement} div O elemento div para receber o template renderizado
 * @param {*} dados O modelo de dados para injeção no template
 */
function carregarComModelo(path, div, dados, callback) {
    carregar(path, div, () => { 
        bind(dados); 
        if (callback != null) { callback(); }
    });
}

/**
 * Vincula os dados do modelo com a tela em exibição.
 * @param {*} modelo O modelo de dados a ser renderizado.
 */
function bind(modelo) {
    let elementos = document.getElementsByClassName('bindable');

    Array.prototype.forEach.call(elementos, function(elemento) {
        let key = elemento.getAttribute('data-bind');
        console.log(key);
        console.log(modelo[key]);
        elemento.value = modelo[key];
        elemento.innerText = modelo[key];
    });
}

/**
 * Filtra o cliente com base no conteúdo informado.
 */
function filtroCliente() {
    let valor = document.getElementById('inputFiltroCliente').value;
    obterClientes(valor);
}

/**
 * Obtém a coleção de clientes, com opção de ciltro
 * @param {string} filtro O filtro a ser aplicado aos clientes
 */
function obterClientes(filtro) {
    fetch('http://localhost:6789/clientes?filtro=' + filtro)
    .then((resposta) => {
        if (resposta.status == 200) { return resposta.json(); }
        else { console.log(resposta.status); return []; }
    })
    .then((dados) => {
        carregarClientes(dados);
    });
}

/**
 * Carega os pets do cliente (callback)
 * @param {*} idCliente O id do cliente
 */
function carregarPets(idCliente) {

    let tabela = document.getElementById('tabelaPets');
    let tbody = tabela.getElementsByTagName('tbody')[0];

    tbody.innerHtml = '';
    tbody.innerText = '';

    fetch('http://localhost:6789/clientes/' + idCliente + '/animais')
    .then((resposta) => {
        if (resposta.status == 200) { return resposta.json(); }
        else { console.log(resposta.status); return []; }
    })
    .then((dados) => {
        dados.forEach(item => {
            let tr = document.createElement("tr");
            tr.setAttribute('pet-id', item.id);
            tbody.appendChild(tr);
    
            let tdNome = document.createElement('td');
            tr.appendChild(tdNome);
            tdNome.innerText = item.nome;
    
            let tdRaca = document.createElement('td');
            tr.appendChild(tdRaca);
            tdRaca.innerText = item.raca;
    
            let tdEspecie = document.createElement('td');
            tr.appendChild(tdEspecie);
            tdEspecie.innerText = item.especie;
    
            let tdAcoes = document.createElement('td');
            tr.appendChild(tdAcoes);
            let a = document.createElement("a");
            a.href="#consultar/" + item.id;
            a.text = "Consultar";
            tdAcoes.appendChild(a);
        });
    });
}

/**
 * Realiza a consulta para um animal
 */
function consultaAnimal() {
    let idAnimal = location.hash.replace('#consultar/', '');
    let dadosConsulta = document.getElementById('inputDadosConsulta').value;

    fetch('http://localhost:6789/animais/' + idAnimal + '/consultas',
    {
        method: 'post',
        body: JSON.stringify({ idAnimal: idAnimal, dadosConsulta: dadosConsulta })
    })
    .then((resposta) => {
        if (resposta.status == 200) { alert('Consulta registrada com sucesso!'); location.href="#visualizarClientes"; }
        else { console.log(resposta.status); alert('Falha no registro da consulta'); }
    });
}

/**
 * Cadastra um cliente
 * @param {Event} evento O evento 
 */
function cadastrarCliente(evento) {
    evento.preventDefault();
    evento.stopPropagation();

    let nome = document.getElementById("inputNome").value;
    let email = document.getElementById("inputEmail").value;
    let telefone = document.getElementById('inputTelefone').value;
    let endereco = document.getElementById('inputEndereco').value;

    fetch('http://localhost:6789/clientes', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
		body: JSON.stringify({ nome: nome, email: email, telefone: telefone, endereco: endereco })
	}).then(function (response){
        if (response.status == 201) {
            alert('Cadastro realizado com sucesso!');
            location.href="#visualizarClientes";
        } else {
            console.log(response);
            alert('Falha no cadastro dos clientes');
        }
	});

    return false;
}

/**
 * Cadastra um animal
 * @param {Event} evento O evento
 */
function cadastrarAnimal(evento) {
    evento.preventDefault();
    evento.stopPropagation();

    let idCliente = location.hash.replace('#cadastrarAnimal/', '');
    let nome = document.getElementById("inputNome").value;
    let raca = document.getElementById('inputRaca').value;
    let especie = document.getElementById('inputEspecie').value;
    let url = 'http://localhost:6789/clientes/' + idCliente + '/animais';
    console.log(url);

    fetch(url, {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
		body: JSON.stringify({ nome: nome, especie: especie, raca: raca, idCliente: idCliente })
	}).then(function (response){
        if (response.status == 201) {
            alert('Cadastro realizado com sucesso!');
            location.href="#visualizarCliente/" + idCliente;
        } else {
            console.log(response);
            alert('Falha no cadastro do pet');
        }
	});

    return false;
}

function cadastrarConsultaAnimal(evento) {
    evento.preventDefault();
    evento.stopPropagation();

    let idAnimal = location.hash.replace('#consultar/', '');
    let dadosConsulta = document.getElementById("inputDadosConsulta").value;
    let url = 'http://localhost:6789/animais/' + idAnimal + '/consultas';

    fetch(url, {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
		body: JSON.stringify({ idAnimal: idAnimal, dadosConsulta: dadosConsulta })
	}).then(function (response){
        if (response.status == 201) {
            alert('Consulta cadastrada com sucesso!');
            location.href="#visualizarClientes";
        } else {
            console.log(response);
            alert('Falha no cadastro da consulta do animal');
        }
	});

    return false;
}