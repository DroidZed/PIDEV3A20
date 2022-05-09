window.onload = () => {
    const typePostFilter = document.querySelector("#typePostFilter") ;


    document.querySelectorAll("#typePostFilter option").forEach( option => {
        option.addEventListener("change" , () => {
           console.log("clic")
        });
    }) ;

}