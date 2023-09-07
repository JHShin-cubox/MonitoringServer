const slideWrap = document.querySelector('.swiper_frame');
const slide = document.querySelectorAll('.grid_wrapper');
// const nav = document.querySelectorAll('.pagination a');

// nav[0].classList.add('active');

const firstEl = slideWrap.firstElementChild;
const lastEl = slideWrap.lastElementChild;
let cloneFirst = firstEl.cloneNode(true);
let cloneLast = lastEl.cloneNode(true);

slideWrap.appendChild(cloneFirst);
slideWrap.insertBefore(cloneLast, slideWrap.firstElementChild);

slideWrap.style.width = `${100*(slide.length+2)}%`;
slideWrap.style.left = '-100%';


const component = document.querySelector('.monitor_content.content_wrap');
let current = 0;
component.addEventListener('click', function(event) {
  const rect = component.getBoundingClientRect();
  const clickX = event.clientX - rect.left;
  // console.log(slideWrap.style.left);
  if (clickX < rect.width / 2) {
    console.log('왼쪽 영역을 클릭했습니다.');

    if(current == 2){
      current--;

      document.querySelector('.tittle').innerText = "Central Server"
      document.querySelector('.reading_graph .num').innerText = "25"
      slideWrap.style.transition = '300ms';
      slideWrap.style.left = `-${100 * (current + 1)}%`;
    }else if(current == 1){
      current--;
      document.querySelector('.reading_graph .num').innerText = "10"
      document.querySelector('.tittle').innerText = "Check PC"
      slideWrap.style.transition = '300ms';
      slideWrap.style.left = `-${100 * (current + 1)}%`;
    }
    else{
      slideWrap.style.transition = '200ms';
      slideWrap.style.left = '0%';//'0%';
      current = slide.length - 1;

      setTimeout(function(){
        slideWrap.style.transition = '0ms';
        slideWrap.style.left = `-${100 * (current + 1)}%`;
      },200)
      document.querySelector('.reading_graph .num').innerText = "30"
      document.querySelector('.tittle').innerText ="TRS"
    }

  } else {
    console.log('오른쪽 영역을 클릭했습니다.');
    if(current == 0){
      // console.log(slide.length - 1);
        current++;

        // nav[current-1].classList.remove('active');
        // nav[current].classList.add('active');
        document.querySelector('.tittle').innerText = "Central Server"
        slideWrap.style.transition = '300ms';
        slideWrap.style.left = `-${100 * (current + 1)}%`;
      }else if(current == 1){
        // console.log(slide.length - 1);
          current++;

          // nav[current-1].classList.remove('active');
          // nav[current].classList.add('active');
          document.querySelector('.tittle').innerText = "TRS"
          slideWrap.style.transition = '300ms';
          slideWrap.style.left = `-${100 * (current + 1)}%`;
        }
      else{
        console.log(current);
        document.querySelector('.tittle').innerText = "Check PC"

        current++;
        slideWrap.style.transition = '200ms';
        slideWrap.style.left = `-${100 * (current + 1)}%`;
        current = 0;
        // nav[slide.length-1].classList.remove('active');
        // nav[current].classList.add('active');
        setTimeout(function(){
          slideWrap.style.transition = '0ms';
          slideWrap.style.left = `-${100 * (current + 1)}%`;
          // slideWrap.style.rigtht = `-${100 * (current + 1)}%`;
        },200);
      }

  }
});