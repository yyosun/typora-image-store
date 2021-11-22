const request = require("request")
const fs = require("fs")
const arguments = process.argv.splice(2)
const req = request.post("http://localhost:8081/upload/typora", function (err, resp, body) {
    const arr = JSON.parse(body) || [];
    if (err) {
        console.log('Error!');
    } else {
        console.log('Upload Success:\n');
        for (let i=0; i< arr.length;i++){
            console.log(arr[i])
        }
    }
})
const form = req.form();
for (let i=0;i<arguments.length;i++)
{
    form.append("file", fs.createReadStream(arguments[i]))
}


