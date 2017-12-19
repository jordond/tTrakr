const fetch = require("node-fetch")

async function get(url) {
  const response = await fetch(url)
  if (response.ok) {
    return response.json()
  }
  throw new Error("Fetch failed!", response.status)
}

module.exports = {
  get
}