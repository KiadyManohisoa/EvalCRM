let customerBudget = 0; // Variable globale à mettre à jour

document.addEventListener("DOMContentLoaded", function () {
    const selectCustomer = document.getElementById("customerId");
    const form = document.getElementById("email-form");
    const amountInput = document.getElementById("amount");
    
    form.addEventListener("submit", function (event) {
        event.preventDefault();
        let expenseAmount = parseFloat(amountInput.value) || 0;
        
        if (shouldShowAlert(customerBudget, alert_rate, expenseAmount)) {
            console.log("nihoatra anle alert_rate");
            alert(`This is an alert for exceeding ${alert_rate}% of the customer budget.`);
        }
        
        if (shouldConfirmSubmission(customerBudget, expenseAmount)) {
            if (!confirm("The expense amount exceeds the customer budget. Do you want to continue?")) {
                console.log("went out");
                amountInput.value = 0;
                return;
            }
        } else if (expenseAmount > customerBudget) {
            return;
        }
        
        form.submit();
    });

    selectCustomer.addEventListener("change", function () {
        fetchCustomerBudget(this.value);
    });
});

function shouldShowAlert(customer_budget, alert_rate, amount) {
    return amount >= (customer_budget * alert_rate / 100) && amount <= customer_budget;
}

function shouldConfirmSubmission(customer_budget, amount) {
    return amount > customer_budget;
}

function fetchCustomerBudget(customerId) {
    if (!customerId) return;
    
    fetch(home + `api/customer/budget/${customerId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erreur lors de la récupération du budget");
            }
            return response.json();
        })
        .then(data => {
            customerBudget = data;
            console.log("customerBudget new value is " + customerBudget);
        })
        .catch(error => console.error("Erreur :", error));
}
