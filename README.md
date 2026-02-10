# Homeworld Backend

1. [Intro](#intro)
2. [Setup and Running](#setup-and-running)
3. [Login and Registration](#login-and-registration)
4. [Cards](#cards)
5. [Players](#players)
6. [Deck](#deck)

## Intro
This is the backend code for a deckbuilder website for the trading card game Grand Archive by the Weebs Of The Shore LLC.

## Setup and Running
Command to start the server is .\gradlew bootrun

## Login and Registration
**POST /auth/register**  
Allows users to make a new account. Body should be:  
```
{  
    "email": "{email here}",  
    "password": "{password here}",  
	"name": "{display name here}"  
}  
```
**POST /auth/login**  
Returns an authentication token. Expected to be returned in Header as:  
```
{  
    "Authorization": "Bearer {token here}"  
}  
```
Body should be:  
```
{  
    "email": "{email here}",  
    "password": "{password here}"  
}
```
**POST /auth/logout**  
Logs user out by blacklisting their current token. Requires auth token

## Cards
**GET /api/card**  
Returns the data for 15 unfiltered cards, or less if near the end. Accepts an integer parameter "page".  
Response is returned the following structure:  
```
{
    "data": [
        {
            "classes": [
                "WARRIOR"
            ],
            "costMemory": 1,
            "costReserve": null,
            "durability": null,
            "elements": [
                "NORM"
            ],
            "effect": "**Inherited Effect** — Whenever CARDNAME attacks with a Polearm weapon and/or Polearm attack card, target Horse or Human ally you control gets +1 [POWER] until end of turn. *(Your champion has this ability as long as this card is part of its lineage.)*",
            "effectRaw": "Inherited Effect — Whenever Jin attacks with a Polearm weapon and/or Polearm attack card, target Horse or Human ally you control gets +1 POWER until end of turn. (Your champion has this ability as long as this card is part of its lineage.)",
            "flavor": null,
            "lastUpdate": null,
            "legality": null,
            "level": 1,
            "life": 20,
            "name": "Jin, Fate Defiant",
            "power": null,
            "referencedBy": [],
            "references": [],
            "rule": [],
            "speed": null,
            "slug": "jin-fate-defiant",
            "subtypes": [
                "WARRIOR",
                "HUMAN"
            ],
            "types": [
                "CHAMPION"
            ],
            "uuid": "zd8l14052j",
            "editions": [
                {
                    "cardId": "zd8l14052j",
                    "collaborators": [],
                    "collectorNumber": "004",
                    "configuration": "default",
                    "effect": null,
                    "effectHtml": null,
                    "effectRaw": null,
                    "flavor": "\"Why fight to die when you can fight to live?\"",
                    "illustrator": "十尾",
                    "image": "/cards/images/3z8m7n5voz.jpg",
                    "orientation": null,
                    "rarity": 1,
                    "slug": "jin-fate-defiant-amb",
                    "themaCharmFoil": 160,
                    "themaFerocityFoil": 137,
                    "themaFoil": 744,
                    "themaGraceFoil": 150,
                    "themaMystiqueFoil": 103,
                    "themaValorFoil": 194,
                    "themaCharmNonfoil": 14,
                    "themaFerocityNonfoil": 12,
                    "themaNonfoil": 65,
                    "themaGraceNonfoil": 13,
                    "themaMystiqueNonfoil": 9,
                    "themaValorNonfoil": 17,
                    "uuid": "3z8m7n5voz",
                    "otherOrientationCard": [],
                    "cardSetResponseDTO": {
                        "id": "7pk8b8vm9v",
                        "language": "EN",
                        "lastUpdate": "2025-01-18T17:40:17.152+00:00",
                        "name": "Mortal Ambition",
                        "prefix": "AMB",
                        "releaseDate": "2024-10-11T00:00:00"
                    },
                    "set": {
                        "id": "7pk8b8vm9v",
                        "language": "EN",
                        "lastUpdate": "2025-01-18T17:40:17.152+00:00",
                        "name": "Mortal Ambition",
                        "prefix": "AMB",
                        "releaseDate": "2024-10-11T00:00:00"
                    }
                },
                {
                    "cardId": "zd8l14052j",
                    "collaborators": [],
                    "collectorNumber": "003",
                    "configuration": "default",
                    "effect": null,
                    "effectHtml": null,
                    "effectRaw": null,
                    "flavor": "\"Why fight to die when you can fight to live?\"",
                    "illustrator": "十尾",
                    "image": "/cards/images/9iee1ii3wb.jpg",
                    "orientation": null,
                    "rarity": 1,
                    "slug": "jin-fate-defiant-ambsd",
                    "themaCharmFoil": null,
                    "themaFerocityFoil": null,
                    "themaFoil": null,
                    "themaGraceFoil": null,
                    "themaMystiqueFoil": null,
                    "themaValorFoil": null,
                    "themaCharmNonfoil": 14,
                    "themaFerocityNonfoil": 11,
                    "themaNonfoil": 62,
                    "themaGraceNonfoil": 12,
                    "themaMystiqueNonfoil": 8,
                    "themaValorNonfoil": 17,
                    "uuid": "9iee1ii3wb",
                    "otherOrientationCard": [],
                    "cardSetResponseDTO": {
                        "id": "fst6wv2m5a",
                        "language": "EN",
                        "lastUpdate": "2025-01-18T17:40:17.152+00:00",
                        "name": "Mortal Ambition Starter Decks",
                        "prefix": "AMBSD",
                        "releaseDate": "2024-10-11T00:00:00"
                    },
                    "set": {
                        "id": "fst6wv2m5a",
                        "language": "EN",
                        "lastUpdate": "2025-01-18T17:40:17.152+00:00",
                        "name": "Mortal Ambition Starter Decks",
                        "prefix": "AMBSD",
                        "releaseDate": "2024-10-11T00:00:00"
                    }
                },
                {
                    "cardId": "zd8l14052j",
                    "collaborators": [],
                    "collectorNumber": "004",
                    "configuration": "default",
                    "effect": null,
                    "effectHtml": null,
                    "effectRaw": null,
                    "flavor": null,
                    "illustrator": "十尾",
                    "image": "/cards/images/t2bpfx5wbu.jpg",
                    "orientation": null,
                    "rarity": 7,
                    "slug": "jin-fate-defiant-amb-csr",
                    "themaCharmFoil": 401,
                    "themaFerocityFoil": 345,
                    "themaFoil": 1860,
                    "themaGraceFoil": 372,
                    "themaMystiqueFoil": 256,
                    "themaValorFoil": 486,
                    "themaCharmNonfoil": null,
                    "themaFerocityNonfoil": null,
                    "themaNonfoil": null,
                    "themaGraceNonfoil": null,
                    "themaMystiqueNonfoil": null,
                    "themaValorNonfoil": null,
                    "uuid": "t2bpfx5wbu",
                    "otherOrientationCard": [],
                    "cardSetResponseDTO": {
                        "id": "hkwbw1cw1y",
                        "language": "EN",
                        "lastUpdate": "2025-01-18T17:40:17.152+00:00",
                        "name": "Mortal Ambition First Edition",
                        "prefix": "AMB 1st",
                        "releaseDate": "2024-10-11T00:00:00"
                    },
                    "set": {
                        "id": "hkwbw1cw1y",
                        "language": "EN",
                        "lastUpdate": "2025-01-18T17:40:17.152+00:00",
                        "name": "Mortal Ambition First Edition",
                        "prefix": "AMB 1st",
                        "releaseDate": "2024-10-11T00:00:00"
                    }
                }
            ]
        }
    ],
    "page_data_count": 1,
    "total_data_count": 1907,
    "page_number": 4,
    "has_next": true,
    "total_pages": 128
}
```

**GET /api/card/{id}**   
Accepts a card UUID. returns the same response as above, except for that one specific card.   

**GET /api/card/search**   
Searches cards based on the following parameters and returns them in the same response format as above:
```
classes={WARRIOR, CLERIC, ASSASSIN, MAGE, GUARDIAN, TAMER, RANGER}    
costMemory, costReserve, durability, power={Integer}   
costMemoryOperator, costReserveOperator, durabilityOperator, powerOperator={">", ">=", "=", "<=", "<"}   
effect={backend searches for effect text on cards that contain this string}   
setPrefix={set prefix string. refer to Grand Archive Index} can be a list   
rarity={Integer. 1 = common, 2 = uncommon, 3 = rare, 4 = super rare, 5 = ultra rare, 6 = promo, 7 = collector super rare, 8 = collector ultra rare, 9 = collector promo rare}    
thema____Operator={">", ">=", "=", "<=", "<", ____ can be Charm, Mystique, Ferocity, Valor, Grace or Sum}   
thema____={Integer}   
element={NORM, WATER, FIRE, WIND, ASTRA, TERA, CRUX, ARCANE, LUXEM, UMBRA, EXALTED} can be a list   
flavor={backend searches for flavor text on cards that contain this string}   
legality={"STANDARD" only}   
limit={Integer} only if legality is filled in   
limitOperator={">", ">=", "=", "<=", "<"}   
name={String}   
speed={boolean, true = fast, false = slow}   
slug={String}   
subtype={subtype String}   
type={type String}   
id={id String}   
page={integer page}   
```

## Players
**GET /api/player**   
Returns user data in the following format:   
```
{
    "id": "playerId",
    "email": "player email",
    "name": "display name",
    "deckIds": [
        "list of Integer Id"
    ]
}
```

**PUT /api/player**   
Updates the display name of the player   
```
{
    "name": "new name here"
}
```

## Deck
**GET /api/deck**   
A simplified list of user's deck. Returned list looks as follows:   
```
[
    {
        "id": deck_id,
        "display_card_url": "url",
        "hide_status": "PUBLIC"
    }
]
```

**GET /api/deck/random**
Pulls up 20 of the last public decks. Same format as /api/deck

**GET /api/deck/{id}**
Pulls up the deck with the matching deck Id. Only works if the deck is public, private, or is hidden and the user is the deck owner and logged in.   
```
{
    "id": 5,
    "display_card_url": "/cards/images/dyhxw5d9n5.jpg",
    "deck_list": [
        {
            "deckId": 5,
            "card_data_uuid": "q6prapdczd",
            "edition_data_uuid": "dyhxw5d9n5",
            "image_url": "/cards/images/dyhxw5d9n5.jpg"
        }
    ],
    "hide_status": "PUBLIC"
}
```

**POST /api/deck/new**
Creates a new deck under the logged in user. Accepts a body as follows:
```
{
    "hide_status": "PUBLIC",
    "display_edition_id": "dyhxw5d9n5",
    "deck_list": [
        {
            "card_data_uuid": "q6prapdczd",
            "edition_data_uuid": "dyhxw5d9n5",
            "image_url": null
        }, {
            "card_data_uuid": "DpHDGaX2Pn",
            "edition_data_uuid": "8kskierp5t",
            "image_url": null
        }
    ]
}
```
Returns the full decklist like /api/deck/{id}

**PUT /api/deck/{id}**
Updates the deck if the user is logged in and owns the deck. Accepts the following body:
```
{
    "hide_status": "PUBLIC",
    "display_edition_id": "dyhxw5d9n5",
    "deck_list": [
        {
            "card_data_uuid": "q6prapdczd",
            "edition_data_uuid": "dyhxw5d9n5",
            "image_url": null
        }, {
            "card_data_uuid": "DpHDGaX2Pn",
            "edition_data_uuid": "8kskierp5t",
            "image_url": null
        }
    ]
}
```
Returns an updated full decklist like /api/deck/{id}

**DELETE /api/deck/{id}**
Deletes the deck if the user is logged in and owns the deck.