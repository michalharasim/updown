function downloadFile() {
    const url = window.location.href;
    const regex = /\/file\/(.+)/;
    const match = regex.exec(url);
    const id = match ? match[1] : '';
    window.location.href = window.location.href + "/download";
}
