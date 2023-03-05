function uploadFile() {
    const fileInput = document.getElementById("fileChooser");
    if (fileInput.value === "") {
        alert("You need to choose your file before uploading it!")
        return;
    }
    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append("file", file);

    fetch("/upload", {
        method: "POST",
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            alert("The file has been uploaded successfuly!");
            const fileName = data.fileName;
            const fileType = data.fileType;
            const downloadURL = data.downloadURL;
            const fileNameElement = document.getElementById("fileName");
            fileNameElement.textContent = fileName;

            const downloadURLElement =
                document.getElementById("downloadURL");
            downloadURLElement.textContent = downloadURL;
            downloadURLElement.setAttribute("href", downloadURL);
            fileInput.value = "";
        })
        .catch(error => console.error(error));
}
