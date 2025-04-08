document.addEventListener('DOMContentLoaded', function() {
    // Fonction pour récupérer les stats
    function fetchStats() {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8080/api/financial/stat', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        
        xhr.onload = function() {
            if (xhr.status >= 200 && xhr.status < 300) {
                const response = JSON.parse(xhr.responseText);
                if (response.code === 200) {
                    // Mise à jour des valeurs dans le DOM
                    document.getElementById('ticketsCount').textContent = response.data.count_tickets;
                    document.getElementById('leadsCount').textContent = response.data.count_leads;
                    document.getElementById('totalBudget').textContent = response.data.total_depenses + " $";
                    document.getElementById('expensesTotal').textContent = response.data.total_expenses + " $";
                    // Optionnel: Masquer les indicateurs de pourcentage si non disponibles
                    // document.querySelectorAll('.text-success, .text-muted').forEach(el => {
                    //     el.style.display = 'none';
                    // });
                } else {
                    console.error('Erreur du serveur:', response.message);
                }
            } else {
                console.error('Erreur HTTP:', xhr.statusText);
            }
        };
        
        xhr.onerror = function() {
            console.error('Erreur réseau');
        };
        
        // Envoi d'un objet JSON vide car votre endpoint attend un POST
        xhr.send(JSON.stringify({}));
    }

    // Appel initial
    fetchStats();
    
    // Optionnel: Rafraîchir périodiquement (toutes les 5 minutes)
    setInterval(fetchStats, 300000);
});