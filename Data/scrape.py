"""Wikipedia scraper using their API

This scraper role is to recieve a tag and return its
description, using Wikipedia's API: MediaWiki API.

    Functions
    -------
    get_description(tag: str, s: requests.Session) -> str
        Recieves a tag as parameter and returns the description of the tag obtained from the API.
        Parameters
        ----------
        tag: str
            Tag to be searched.
        session: requests.Session
            Session to be passed in the main script.
"""
import requests

URL = "https://en.wikipedia.org/w/api.php"  # Engish Wikipedia API endpoint

# A lot of tunning has gone into it and they return good results for the most part. Better not touch.
PARAMS = {
    "action": "query",
    "generator": "prefixsearch",
    "gpssearch": None,
    "format": "json",
    "prop": "extracts",
    "exintro": 1,
    "explaintext": 1,
    "redirects": 1,
    "exchars": 300,
    "cmtitle": "Category:Computer science"
}


def get_description(tag: str, session: requests.Session, url: str = URL, params: dict = PARAMS) -> str:
    """Returns description of a tag
    Parameters
    ----------
    tag: str
        Tag to be searched.
    session: requests.Session
        Session to be passed in the main script.
    """
    params["gpssearch"] = tag
    data = session.get(url=url, params=params).json()

    # Check if query returns data:
    if "query" not in data:
        return f"No description has been found on Wikipedia, please manually search for {tag}."

    # Extract unknown key (each query returns a random number as key).
    key = list(data["query"]["pages"])[0]

    description = data["query"]["pages"][key]["extract"]

    return description


if __name__ == "__main__":
    test_tags = ["Algorithm",
                 "Big O notation",
                 "Turing Complete",
                 "cpu-pipelines",
                 "computer-architecture",
                 "database-theory",
                 "average-case",
                 "time-complexity"
                 ]

    with requests.Session() as s:
        for t in test_tags:
            desc = get_description(t, s)
            # print(
            #    f"Tag: {t}.\nDescription: {desc}\n == == == == == == == == == == == == == == ==\n")
