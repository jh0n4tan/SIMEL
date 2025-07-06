const userDropdown = document.getElementById('userDropdown');
    const dropdownArrow = document.getElementById('dropdownArrow');

    userDropdown.addEventListener('click', function () {
        const isExpanded = userDropdown.getAttribute('aria-expanded') === 'true';

        // Esperamos un poquito para asegurarnos que el dropdown ya cambió
        setTimeout(() => {
            if (isExpanded) {
                dropdownArrow.classList.remove('fa-chevron-up');
                dropdownArrow.classList.add('fa-chevron-down');
            } else {
                dropdownArrow.classList.remove('fa-chevron-down');
                dropdownArrow.classList.add('fa-chevron-up');
            }
        }, 100);
    });