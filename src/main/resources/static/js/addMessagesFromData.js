
async function addMessage() {
  const val = document.getElementById("messages");
  const name = document.getElementById("name").value;
  const message = document.getElementById("message").value;

  const messageBody = '<div class="message-element">' +
                    '<h4>' + 'Name:' + name + '</h4>' +
                    '<p>' + 'Message: ' + message + '</p>' +
                    '<p>' + 'Data: '+ new Date().toLocaleString()  + '</p>' +
                    '</div>' + '<td>'
  val.innerHTML += messageBody;
}

