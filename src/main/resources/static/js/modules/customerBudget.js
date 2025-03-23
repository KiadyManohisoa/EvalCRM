let customerBudget = 0; // Variable globale à mettre à jour

function isAmountAboveAlertRate(customer_budget,the_alert_rate, the_amount) {
    if(the_amount > ((customerBudget * the_alert_rate) / 100.0)) {
        return true;
    }
    return false;
}

document.addEventListener("DOMContentLoaded", function () {
    
    const selectCustomer = document.getElementById("customerId");
    const form = document.getElementById("email-form");
    console.log("alert_rate value is " + alert_rate);

    form.addEventListener("submit", function(event){
        event.preventDefault();
        let expense_amount = document.getElementById("amount").value;
        if(isAmountAboveAlertRate(customerBudget, alert_rate, expense_amount)) {
            if(!confirm(`This an alert for going beyond the alert rate of ${alert_rate}%. Do you want to continue?`)) {
                return;
            }
        }
        form.submit();

    });

    selectCustomer.addEventListener("change", function () {
        const customerId = this.value;

        if (customerId) {
            fetch( home + `api/customer/budget/${customerId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Erreur lors de la récupération du budget");
                    }
                    return response.json();
                })
                .then(data => {
                    customerBudget = data;
                    console.log("customerBudget new value is "+customerBudget); 
                })
                .catch(error => console.error("Erreur :", error));
        }
    });
});
