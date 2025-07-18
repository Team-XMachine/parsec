const canvas = document.getElementById("field");
const ctx = canvas.getContext("2d");
const motorPanel = document.getElementById("motorPanel");

const fieldImage = new Image();
fieldImage.src = "field.jpg";

let robotPose = { x: 800, y: 800, heading: 0 };
let motors = [];

fieldImage.onload = draw;

const socket = new WebSocket("ws://192.168.43.1:8082");

socket.onmessage = (event) => {
  const data = JSON.parse(event.data);
  if (data.type === "telemetry") {
    robotPose = data.pose;
    motors = data.motors;
    draw();
    updateMotorPanel();
  }
};

function draw() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  ctx.drawImage(fieldImage, 0, 0, canvas.width, canvas.height);

  const { x, y, heading } = robotPose;
  ctx.save();
  ctx.translate(x, y);
  ctx.rotate((heading * Math.PI) / 180);

  ctx.fillStyle = "#00ff00";
  ctx.fillRect(-40, -40, 80, 80);
  ctx.restore();
}

function updateMotorPanel() {
  motorPanel.innerHTML = "<h2>Motors</h2>";
  motors.forEach(m => {
    motorPanel.innerHTML += `
      <div style="background:#333;padding:10px;margin:10px;border-radius:6px">
        <strong>${m.name}</strong><br>
        Power: ${m.power.toFixed(2)}<br>
        Velocity: ${m.velocity}<br>
        Position: ${m.position}
      </div>
    `;
  });
}
