const fs = require("fs")
const path = require("path")
const yargs = require("yargs")
const fetch = require("whatwg-fetch")

const beep = require("./api")

// beep.getTeams().then(x => fs.writeFileSync(path.resolve("./teams.json"), JSON.stringify(x, null, 2)))

beep.getTeams()
  .then(beep.getPlayersFromTeamData)
  .then(x => fs.writeFileSync(path.resolve("./teams-players.json"), JSON.stringify(x, null, 2)))