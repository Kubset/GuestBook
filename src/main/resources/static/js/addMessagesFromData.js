//no longer used method
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

async function createDivBodyFromJson(jsonPath) {
    const divBody = document.getElementById("messages");
    const messagesJson = await getJsonFromPath(jsonPath);

    if (messagesJson.messages.length === 0) {
      return null;
    }
     addToBody(messagesJson, divBody);
}

async function getJsonFromPath(path) {
    const response = await fetch(path);
    return response.json();
}

function addToBody(messagesJson, divBody) {

  for (let i = 0; i < messagesJson.messages.length; i++) {
    const message = messagesJson.messages[i];

    const messageBody = '<div class="message-element">' +
                    '<h4>' + 'Name:' + message.name + '</h4>' +
                    '<p>' + 'Message: '+ message.message + '</p>' +
                    '<p>' + 'Data: ' + message.data + '</p>' +
                    '</div>';

      divBody.innerHTML += messageBody;
  }
}

createDivBodyFromJson("static/data/exampleData.json");