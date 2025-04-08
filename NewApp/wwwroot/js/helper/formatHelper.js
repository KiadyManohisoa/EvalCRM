function formatNumber(number,unity) {
    let rep = unity;
    number = number.toString();
    
    let decimalPart = '';
    if (number.includes(',')) {
        let parts = number.split(',');
        number = parts[0];        
        decimalPart = ',' + parts[1];
    }

    else if (number.includes('.')) {
        let parts = number.split('.');
        number = parts[0];        
        decimalPart = '.' + parts[1];
    }

    let count = 0;

    for (let i = number.length - 1; i >= 0; i--) {
        if (count === 3) {
            rep += ' '; 
            rep += number[i];
            count = 1; 
        } else {
            rep += number[i];
            count++;
        }
    }

    let result = rep.split('').reverse().join('');

    result += decimalPart;

    return result;
}

function formatNumericData(rows,colIndex) {
    for(let i=0;i< rows.length;i++) {
        var cells = rows[i].getElementsByTagName('td');
        if(cells[colIndex]) {
            cells[colIndex].textContent = formatNumber(cells[colIndex].textContent,'');
        }
    }
}

function styleTableColumns(table) {
    const rows = table.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    //const rows = table.getElementsByTagName('tr');

    if (rows.length === 0) return;

    const firstRowCells = rows[0].getElementsByTagName('td');
    
    for (let colIndex = 0; colIndex < firstRowCells.length; colIndex++) {
        const cell = firstRowCells[colIndex];
        const cellText = firstRowCells[colIndex].textContent.trim();
        
        if (isNumeric(cellText)) {
            applyColumnStyle(table, colIndex, 'right');
            formatNumericData(rows,colIndex,cellText);

        } else if (isDate(cellText) || isTimestamp(cellText)) {
            applyColumnStyle(table, colIndex, 'center');
        } else {
            let hasAnchorOrImage = false;
            for (let i = 0; i < cell.childNodes.length; i++) {
                const child = cell.childNodes[i];
                if (child.nodeName.toLowerCase() === 'a' || child.nodeName.toLowerCase() === 'img') {
                    hasAnchorOrImage = true;
                    break;
                }
            }

            if (hasAnchorOrImage) {
                applyColumnStyle(table, colIndex, 'center');
            } else {
                applyColumnStyle(table, colIndex, 'left');
            }
        }
    }
}

function applyColumnStyle(table, colIndex, alignment) {
    const rows = table.getElementsByTagName('tr');
    rows[0].getElementsByTagName('th')[colIndex].style.textAlign=alignment;
    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        if (cells[colIndex]) {
            cells[colIndex].style.textAlign = alignment;
        }
    }
}

function isNumeric(value) {
    value = value.replace(/\./g, '').replace(',', '.');
    return !isNaN(value) && !isNaN(parseFloat(value));
}

function isDate(value) {
    const date = Date.parse(value);
    return !isNaN(date);
}

function isTimestamp(value) {
    return /^(\d{2})\/(\d{2})\/(\d{4}) (\d{2}):(\d{2})$/.test(value);
}
