    document.addEventListener('DOMContentLoaded', function() {
        const ctx = document.getElementById('expensesChart').getContext('2d');
        
        // Fictitious data
        const ticketAmount = 345228.41;
        const leadAmount = 372882.80;
        const totalAmount = ticketAmount + leadAmount;
        
        const expensesData = {
            labels: ['Tickets', 'Leads'],
            datasets: [{
                data: [ticketAmount, leadAmount],
                backgroundColor: [
                    'rgba(13, 110, 253, 0.8)',  // Bootstrap primary blue
                    'rgba(25, 135, 84, 0.8)'    // Bootstrap success green
                ],
                borderColor: [
                    'rgba(13, 110, 253, 1)',
                    'rgba(25, 135, 84, 1)'
                ],
                borderWidth: 1
            }]
        };

        const expensesChart = new Chart(ctx, {
            type: 'pie',
            data: expensesData,
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'right',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                const value = context.raw;
                                const percentage = Math.round((value / totalAmount) * 100);
                                return `${context.label}: $${value.toLocaleString()} (${percentage}%)`;
                            }
                        }
                    }
                    // },
                    // title: {
                    //     display: true,
                    //     text: 'Expense Distribution by Source',
                    //     font: {
                    //         size: 16
                    //     }
                    // }
                }
            }
        });
    });
