
function validarLetras(field, sizeMax) {
    sizeMax = sizeMax - 1;
    if (field.length > sizeMax) {
        event.returnValue = false;
        event.preventDefault();
    }
    if ((event.keyCode != 32) && (event.keyCode < 65) || (event.keyCode > 90) && (event.keyCode < 97) || (event.keyCode > 122)) {
        event.returnValue = false;
        event.preventDefault();
    }
}
function validarLetrasSimbolos(field, sizeMax) {
    sizeMax = sizeMax - 1;
    if (field.length > sizeMax) {
        event.returnValue = false;
        event.preventDefault();
    }
    if ((event.keyCode != 46) && (event.keyCode != 45) && (event.keyCode != 32) && (event.keyCode < 65) || (event.keyCode > 90) && (event.keyCode < 97) || (event.keyCode > 122))
    {
        event.returnValue = false;
        event.preventDefault();
    }
}
function ValidarNumerosAll(evt, field, sizeMax) {
    alert('entro aqui');
    sizeMax = sizeMax - 1;
    try {
        alert('entro aqui');
        if (field.length > sizeMax) {
            evt.returnValue = false;
        }
        if ((evt.keyCode < 48) || (evt.keyCode > 57)) {
            evt.returnValue = false;
        }
        alert('paso try');
    } catch (ex) {
        if (field.length > sizeMax) {
            event.returnValue = false;
            event.preventDefault();
        }
        if ((event.keyCode < 48) || (event.keyCode > 57)) {
            event.returnValue = false;
            event.preventDefault();
        }
         alert('paso catch');
    }
}

function ValidarNumeros(field, sizeMax) {
    sizeMax = sizeMax - 1;
    if (field.length > sizeMax) {
        event.returnValue = false;
        event.preventDefault();
    }
    if ((event.keyCode < 48) || (event.keyCode > 57)) {
        event.returnValue = false;
        event.preventDefault();
    }
}

function ValidarNumerosDouble(field, sizeMax) {
    sizeMax = sizeMax - 1;
    if (field.length > sizeMax) {
        event.returnValue = false;
        event.preventDefault();
    }
    if ((event.keyCode < 48) || (event.keyCode > 57)) {
        event.returnValue = false;
        event.preventDefault();
    }
}


function validarAlfanumerico(field, sizeMax) {
    sizeMax = sizeMax - 1;
    field = field.toUpperCase();
    if (field.length > sizeMax) {
        event.returnValue = false;
        event.preventDefault();
    }
    if ((event.keyCode < 48) || (event.keyCode > 57) && ((event.keyCode != 32) && (event.keyCode < 65) || (event.keyCode > 90) && (event.keyCode < 97) || (event.keyCode > 122))) {
        event.returnValue = false;
        event.preventDefault();
    }
}

function validarIdentificacion(field, dato) {
    if (dato == 'ruc') {
        if (field.length > 12) {
            event.returnValue = false;
            event.preventDefault();
        }
        if ((event.keyCode < 48) || (event.keyCode > 57)) {
            event.returnValue = false;
            event.preventDefault();
        }
    } else {
        if (dato == 'cedula') {
            if (field.length > 9) {
                event.returnValue = false;
                event.preventDefault();
            }
            if ((event.keyCode < 48) || (event.keyCode > 57)) {
                event.returnValue = false;
                event.preventDefault();
            }
        } else {
            if (field.length > 29) {
                event.returnValue = false;
                event.preventDefault();
            }
            if ((event.keyCode < 48) || (event.keyCode > 57) && ((event.keyCode != 32) && (event.keyCode < 65) || (event.keyCode > 90) && (event.keyCode < 97) || (event.keyCode > 122))) {
                event.returnValue = false;
                event.preventDefault();
            }
        }
    }
}






