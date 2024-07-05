document.addEventListener('DOMContentLoaded', function () {
    const tabs = document.querySelectorAll('.myaccount-tab-menu a');
    const currentUrl = window.location.pathname;

    tabs.forEach(tab => {
        tab.classList.remove('active');
        const tabUrl = new URL(tab.href).pathname;

        if (tabUrl === currentUrl) {
            tab.classList.add('active');
        }

        tab.addEventListener('click', function () {
            tabs.forEach(t => t.classList.remove('active'));
            this.classList.add('active');
        });
    });
});
