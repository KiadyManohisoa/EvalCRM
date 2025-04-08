document.getElementById('toggleSidebar').addEventListener('click', function() {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');
    
    sidebar.classList.toggle('collapsed');
    content.classList.toggle('expanded');
});