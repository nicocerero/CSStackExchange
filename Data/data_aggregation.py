"""Main script to scrape and aggregate data.
"""

from requests.sessions import Session
import requests
import json
import pandas as pd

import scrape


def main():
    """ Tags JSON structure:
        [{
            Id: string,
            TagName: string,
            Count: string
            (sometimes more attributes depending on the type of tag,
                but those are irrelevant for the project and will be deleted)
        }]

        A new attribute "Description" will be added.
        """


def test():
    df = pd.read_json("Datasets/prueba.json")
    df = df.reindex(columns=["Id", "TagName", "Description"])
    df = df.astype({"Description": str})

    with requests.Session() as s:
        for index_label, row_series in df.iterrows():
            tag = df.at[index_label, "TagName"]
            description = scrape.get_description(tag, s)
            df.at[index_label, "Description"] = description

    result = df.to_json(orient="records")
    parsed = json.loads(result)

    with open("Datasets/prueba_resultdos.json", "w", encoding="utf-8") as f:
        json.dump(parsed, f, indent=4)


if __name__ == "__main__":
    test()
