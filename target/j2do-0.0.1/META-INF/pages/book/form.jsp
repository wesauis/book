<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:if test="${isNew}">
            <title>Novo Livro</title>
        </c:if>
        <c:if test="${!isNew}">
            <title>Editar Livro #${book.id()}</title>
        </c:if>

        <link rel="stylesheet" href="<%=request.getContextPath()%>css/common.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>css/book/form.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="<%=request.getContextPath()%>utils.js"></script>
    </head>
    <body>
        <form onsubmit="salvar(event)" method="POST" enctype="application/json" action="<%=request.getContextPath()%>${endpoint}">
            <input name="id" value="${book.id()}" hidden type="hidden">
            <input name="__method" value="${method}" hidden type="hidden">
            <div>
                <label for="title">Título <span class="text-danger">*</span></label>
                <input id="title" name="title" value="${book.title()}" type="text" onchange="hideMessage('title')">
                <p class="hidden input-error text-danger">O título é obrigatório</p>
            </div>
            <div>
                <label for="stars">Estrelas</label>
                <input id="stars" name="stars" value="${book.stars()}" type="range" min="0" max="10">
            </div>
            <div>
                <label for="summary">Resumo</label>
                <textarea id="summary" name="summary" rows="12">${book.summary()}</textarea>                
            </div>
            <div>
                <label for="release_date">Data de Lançamento</label>
                <input id="release_date" name="release_date" value="${book.release_date()}" type="date">                
            </div>
            <div>
                <label for="author">Autor</label>
                <input id="author" name="author" value="${book.author()}" type="text">
            </div>
            <div>
                <label for="publisher">Editora</label>
                <input id="publisher" name="publisher" value="${book.publisher()}" type="text">
            </div>
            <div>
                <label for="page_count">Número de Páginas</label>
                <input id="page_count" name="page_count" value="${book.page_count()}" type="number" min="0">
            </div>
            <button type="submit">Salvar</button>
        </form>

        <script>
            const $form = $('form');

            function showMessage(campo) {
                $(`[name=\${campo}] + .input-error`).classList.toggle('hidden', false);
            }

            function hideMessage(campo) {
                $(`[name=\${campo}] + .input-error`).classList.toggle('hidden', true);
            }

            const validators = {
                title: title => !!title.trim().length
            }

            async function salvar(event) {
                event.preventDefault();

                const campos = $$('form input[name], form textarea[name]').map(campo => {
                    return {
                        name: campo.name,
                        value: campo.value,
                    }
                });

                let ok = true;
                campos.forEach(campo => {
                    if (!validators[campo.name])
                        return;

                    if (!validators[campo.name](campo.value)) {
                        showMessage(campo.name);
                        ok = false;
                    }
                })

                if (ok) {
                    const dados = {};
                    campos.forEach(campo => (dados[campo.name] = campo.value));

                    window.location = await fetch($form.action, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        method: "POST",
                        body: new URLSearchParams(dados),
                    }).then(res => res.text())
                }
            }
        </script>   
    </body>
</html>

