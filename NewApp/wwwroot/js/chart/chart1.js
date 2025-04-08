document.addEventListener('DOMContentLoaded', function() {
    const ctx = document.getElementById('salesChart').getContext('2d');
    
    initializeChart();
    
    const form = document.querySelector('form');
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const lowerDate = document.getElementById('min_date').value;
        const upperDate = document.getElementById('max_date').value;
        
        if (!lowerDate || !upperDate) {
            alert('Veuillez sélectionner les dates.');
            return;
        }
        
        try {
            const response = await fetch(`http://localhost:8080/api/tickets/by-day?start=${lowerDate}&end=${upperDate}`);
            if (!response.ok) {
                throw new Error('Erreur lors de la récupération des données');
            }
            
            const result = await response.json();
            console.log("Données récupérées : ", result);
            
            const data = result.data; // Les données par date
            const labels = Object.keys(data); // Les dates
            const ticketCounts = labels.map(date => {
                // Compter les tickets pour chaque date
                return data[date].filter(t => t.status === 'open').length; // Compter les tickets ouverts
            });
            const leadCounts = labels.map(date => {
                // Compter les leads pour chaque date, ici vous devrez ajouter une logique pour filtrer les leads
                return data[date].filter(t => t.priority === 'medium').length; // Exemple : on considère que les leads ont une priorité 'medium'
            });
            
            updateChart(labels, ticketCounts, leadCounts);
        } catch (error) {
            console.error('Erreur de chargement des données:', error);
        }
    });
    
    function initializeChart() {
        window.salesChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: [
                    {
                        label: 'Tickets',
                        data: [],
                        borderColor: 'rgb(75, 192, 192)',
                        backgroundColor: 'rgba(75, 192, 192, 0.5)',
                        tension: 0.4,
                    },
                    {
                        label: 'Leads',
                        data: [],
                        borderColor: 'rgb(255, 99, 132)',
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        tension: 0.4,
                    }
                ]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'top' }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: 'Nombre' }
                    },
                    x: {
                        title: { display: true, text: 'Date' }
                    }
                }
            }
        });
    }
    
    function updateChart(labels, tickets, leads) {
        window.salesChart.data.labels = labels;
        window.salesChart.data.datasets[0].data = tickets;
        window.salesChart.data.datasets[1].data = leads;
        window.salesChart.update();
    }
});
