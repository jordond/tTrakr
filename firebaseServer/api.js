const fetch = require("node-fetch")

async function get(url, options = {}) {
  const response = await fetch(url, options)
  if (response.ok) {
    return response.json()
  }
  throw new Error("Fetch failed!", response.status)
}

const API_NHL_TEAMS = "http://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=nhl"
const API_LEAGUE_ID = 4380
const API_LEAGUE_EVENTS = `http://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=${API_LEAGUE_ID}`

// http://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4380

const API_PLAYERSAPI = `https://api.mysportsfeeds.com/v1.1/pull/nhl/2016-2017-regular/roster_players.json?fordate=20161117`

const fetchOptions = {
  headers: {
    "Authorization": `Basic ${Buffer.from("jordond" + ":" + "mymonkeys").toString("base64")}`
  }
}

const abbrevMap = {
  "Anaheim Ducks": "ANA",
  "Arizona Coyotes": "ARI",
  "Boston Bruins": "BOS",
  "Buffalo Sabres": "BUF",
  "Calgary Flames": "CGY",
  "Carolina Hurricanes": "CAR",
  "Chicago Blackhawks": "CHI",
  "Colorado Avalanche": "COL",
  "Columbus Blue Jackets": "CBJ",
  "Dallas Stars": "DAL",
  "Detroit Red Wings": "DET",
  "Edmonton Oilers": "EDM",
  "Florida Panthers": "FLO",
  "Los Angeles Kings": "LAK",
  "Minnesota Wild": "MIN",
  "Montreal Canadiens": "MTL",
  "Nashville Predators": "NSH",
  "New Jersey Devils": "NJD",
  "New York Islanders": "NYI",
  "New York Rangers": "NYR",
  "Ottawa Senators": "OTT",
  "Philadelphia Flyers": "PHI",
  "Pittsburgh Penguins": "PIT",
  "San Jose Sharks": "SJS",
  "St. Louis Blues": "STL",
  "Tampa Bay Lightning": "TBL",
  "Toronto Maple Leafs": "TOR",
  "Vancouver Canucks": "VAN",
  "Washington Capitals": "WSH",
  "Winnipeg Jets": "WPJ",
}

async function getTeams() {
  const { teams } = await get(API_NHL_TEAMS)
  return teams.map(y => ({
      abbrev: abbrevMap[y.strTeam],
      name: y.strTeam,
      formed: y.intFormedYear,
      home: {
        stadium: y.strStadium,
        location: y.strStadiumLocation,
        thumbnail: y.strStadiumThumb
      },
      description: y.strDescriptionEN,
      images: {
        badge: y.strTeamBadge,
        jersey: y.strTeamJersey,
        logo: y.strTeamLogo
      }
    })
  ).filter(x => x.name !== "Vegas Golden Knights");
}

async function getPlayersFromTeamData(teamData) {
  try {
    if (!teamData || !teamData.length) throw new Error("Team data is required!")
    const data = []
    console.log(`Fetching player data for ${teamData.length} teams`)
    let index = 0;
    for (team of teamData) {
      index++;
      const url = `${API_PLAYERSAPI}&team=${team.abbrev.toLowerCase()}`
      const { rosterplayers: { playerentry: unMappedPlayers = [] } } = await get(url, fetchOptions)
      if (unMappedPlayers.length) {
        const players = unMappedPlayers.map(x => ({ ...x.player }))
        data.push({...team, players })
        console.log(`Fetched ${index}/${teamData.length}`)
      } else {
        console.error(`Failed to fetch ${index}/${teamData.length} -> ${team.abbrev}`)
      }
    }
    return data;
  } catch (error) {
    console.log(error)
  }
}

// async function getPastEvents() {
//   try {
//     const { events } = await get(API_LEAGUE_EVENTS)
//     return events.map(x => ({
//       id: x.idEvent,
//       name: x.strEvent,
//       home: {
//         id: x.idHomeTeam,
//         name: x.strHomeTeam,
//         score: x.intHomeScore
//       },
//       away: {
//         id: x.idAwayTeam,
//         name: x.strAwayTeam,
//         score: x.intAwayScore
//       },
//       date: x.dateEvent
//     }))
//   } catch (error) {
//     console.log("uhoh - Failed to grab past events", error)
//   }
// }

module.exports = {
  getTeams,
  getPlayersFromTeamData
}
