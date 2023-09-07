function swiper() {
    let container = document.querySelector(".swiper_frame");
    let items = document.querySelectorAll(".grid_wrapper");
    let isDragging = false;
    let startPos = 0;
    let currentTranslate = 0;
    let prevTranslate = 0;
    let currentIndex = 0;
    let animationID;
    
    container.addEventListener("mousedown", (e) => {
        startPos = e.pageX - currentTranslate; 
        isDragging = true;
        animationID = requestAnimationFrame(animation);
        container.style.cursor = 'grabbing';
    });
    
    container.addEventListener("mouseup", () => {
        container.style.cursor = 'grab';
        isDragging = false;
    
        currentIndex = -Math.round(currentTranslate / (container.offsetWidth / items.length));
        
        if (currentIndex >= items.length) {
            currentIndex = 0;
        } else if (currentIndex < 0) {
            currentIndex = items.length - 1;
        }
    
        prevTranslate = currentIndex * (container.offsetWidth / items.length);
        setContainerPosition();
        container.style.cursor = 'grab';
        cancelAnimationFrame(animationID);
    
    });
    function animation() {
        setContainerPosition();
        if (isDragging) requestAnimationFrame(animation);
    }
    
    function setContainerPosition() {
        container.style.transform = `translateX(${currentTranslate}px)`;
    }
    
    container.addEventListener("mousemove", (e) => {
        if (isDragging) {
            if (!isDragging) return;
            currentTranslate = e.pageX - startPos;
    
            if (currentTranslate > 0) {
                currentIndex = items.length - 1;
                currentTranslate = currentIndex * -(container.offsetWidth / items.length);
            } else if (currentTranslate < (items.length - 1) * -(container.offsetWidth / items.length)) {
                currentIndex = 0;
                currentTranslate = 0;
            }
        }
    
    });
    
    
    container.style.scrollBehavior = 'smooth';
}

swiper();
