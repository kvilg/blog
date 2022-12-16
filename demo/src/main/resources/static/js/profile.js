import { getCookie } from './cookie.js';

const name = document.querySelector('.name-rest');
const login = document.querySelector('.login-rest');
const img = document.querySelector('.img-tr');

const btnUpdateImgUser = document.getElementById('btn-main-foto');

const btnAddPost = document.getElementById('btn-main-post');

const btnUpdatePost = document.getElementById('btn-main-update-post');



function render() {
    (async() => {

        let response = await fetch('/auth/login/jwt', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': getCookie('TOKEN')
            }
        });

        if (response.ok) {

            let json = await response.json();



            name.innerHTML = json.name;
            login.innerHTML = json.login;
            img.src = json.img;

        } else {
            alert("Ошибка HTTP: ");
        }

    })();

    //  getPosts();

}

render();


// function getPosts() {
//     (async() => {

//         let response = await fetch('/get/posts', {
//             method: 'GET',
//             headers: {
//                 'Content-Type': 'application/json;charset=utf-8',
//                 'Authorization': getCookie('TOKEN')
//             }
//         });

//         if (response.ok) {

//             let json = await response.json();

//             console.log(json);

//             console.log("ddsdsd");

//             let posts = document.getElementById('posts');
//             posts.innerHTML = ``;

//             for (let i = 0; i < json.posts.length; i++) {

//                 posts.innerHTML +=
//                     `<div class="block-base-r">
//                     <div class="block-img">
//                         <img class="block-img-tr" src="` + json.posts[i].img + `" alt="">
//                     </div>
//                     <div class="text-block">
//                         ` + json.posts[i].text + `
//                     </div>
//                 </div>`;

//             }





//         } else {
//             alert("Ошибка HTTP: ");
//         }

//     })();
// }

btnUpdateImgUser.addEventListener("click", () => {

    let modal = document.querySelector('.modal');

    let modalB = document.querySelector('.modalB');

    modal.classList.add('active');
    modalB.classList.add('activeB');







    document.querySelector('#file').addEventListener('change', () => {
        document.getElementById('btnRelode').addEventListener("click", () => {
            fetchFunc();

        })
    });



    document.querySelector('.modalB').addEventListener("click", () => {




        modal.classList.remove('active');
        modalB.classList.remove('activeB');

    });



    function fetchFunc() {
        var file = document.getElementById('file').files[0];
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function() {




            (async() => {

                let img = {
                    img: reader.result
                };

                let response = await fetch('/update/img', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8',
                        'Authorization': getCookie('TOKEN')
                    },
                    body: JSON.stringify(img)
                });

                if (response.ok) {
                    render();
                } else {
                    render();
                }

            })()
        }
    }





});

btnAddPost.addEventListener("click", () => {

    let modal = document.querySelector('.modalPost');

    let modalB = document.querySelector('.modalB');

    modal.classList.add('active');
    modalB.classList.add('activeB');







    document.querySelector('#filePost').addEventListener('change', () => {
        document.getElementById('btnRelodePost').addEventListener("click", () => {
            fetchFunc();
            render();
        })
    });



    document.querySelector('.modalB').addEventListener("click", () => {




        modal.classList.remove('active');
        modalB.classList.remove('activeB');

    });



    function fetchFunc() {
        var file = document.getElementById('filePost').files[0];
        var reader = new FileReader();
        let textPost = document.getElementById('textPost').value;
        reader.readAsDataURL(file);


        reader.onload = function() {





            (async() => {

                let img = {
                    img: reader.result,
                    text: textPost
                };

                let response = await fetch('/add/post', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8',
                        'Authorization': getCookie('TOKEN')
                    },
                    body: JSON.stringify(img)
                });

                if (response.ok) {
                    console.log('ok');
                } else {
                    alert("Ошибка HTTP: ");
                }

            })()
        }
    }




});


let countsPostUpdate = 0;
btnUpdatePost.addEventListener("click", () => {
    (async() => {

        let img = {
            counts: countsPostUpdate
        };

        let response = await fetch('/get/posts', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'user': login.innerHTML,
                'counts': countsPostUpdate
            }
        });

        if (response.ok) {
            console.log('ok');

            countsPostUpdate = countsPostUpdate + 1;



            let json = await response.json();

            console.log(json);

            console.log("ddsdsd");

            let posts = document.getElementById('posts');


            for (let i = 0; i < json.posts.length; i++) {

                posts.innerHTML +=
                    `<div class="block-base-r">
                    <div class="block-img">
                        <img class="block-img-tr" src="` + json.posts[i].img + `" alt="">
                    </div>
                    <div class="text-block">
                        ` + json.posts[i].text + `
                    </div>
                </div>`;

            }









        } else {
            alert("Ошибка HTTP: ");
        }

    })()


});