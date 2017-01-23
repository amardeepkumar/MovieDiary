package com.udacity.moviediary.utility;

/**
 * Created by Amardeep on 12/12/2016.
 */
public class Constants {


    public interface BundleKeys {
        String ID = "com.udacity.moviediary." + "id";
        String SORT_PREFERENCE = "com.udacity.moviediary." + "sort_preference";
        String TAB_NAME =  "com.udacity.moviediary." +  "tab_name";
        String IS_FAVOURITE_MODE = "com.udacity.moviediary." + "is_fav";
    }

    public interface SortPreference {
        int SORT_BY_POPULARITY = 1001;
        int SORT_BY_RELEASE_DATE = 1002;
        int SORT_BY_RATING = 1003;
        int SORT_BY_NAME = 1004;
    }

    public interface AnalyticsKeys {
        String LOAD_MORE_MOVIE = "load_more_movie";
        String LOAD_MOVIE_DETAILS = "load_movie_details";
        String MOVIE_DETAILS_FAV_CLICKED = "movie_details_fav_clicked";
        String PAGE_NUMBER = "page_number";
        String MOVIE_ID = "movie_id";
        String IS_FAVOURITE = "is_favourite";
        String WATCH_HISTORY_CLICKED = "watch_history_clicked";
        String TRAILER_CLICKED = "trailer_clicked";
        String WATCH_HISTORY_DELETED = "watch_history_deleted";
    }

    public static final String PREV_SELECTION = "prev_selection";

    public static final String WIDGET_RESPONSE = "{\n" +
            "  \"page\": 1,\n" +
            "  \"results\": [\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/bKudNA7yp1iMch9FidHCqiGASRh.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"The content of this film is currently a secret, due to be revealed only when the title is released in 2115.\",\n" +
            "      \"release_date\": \"2099-12-31\",\n" +
            "      \"genre_ids\": [\n" +
            "        \n" +
            "      ],\n" +
            "      \"id\": 401563,\n" +
            "      \"original_title\": \"100 Years\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"100 Years\",\n" +
            "      \"backdrop_path\": \"\\/vusNCN3dFD6nd8aL9ScaJzhzTcu.jpg\",\n" +
            "      \"popularity\": 1.00031,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/2U1lagTa5nyaOcJdRpgpbnouSwf.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"The fifth sequel in the highly acclaimed Avatar series.\",\n" +
            "      \"release_date\": \"2023-12-01\",\n" +
            "      \"genre_ids\": [\n" +
            "        28,\n" +
            "        12,\n" +
            "        14,\n" +
            "        878\n" +
            "      ],\n" +
            "      \"id\": 393209,\n" +
            "      \"original_title\": \"Avatar 5\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Avatar 5\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.002535,\n" +
            "      \"vote_count\": 2,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"A family moves in a old abandoned house, what they don't realize is the house will reveal some disturbing things.\",\n" +
            "      \"release_date\": \"2023-10-10\",\n" +
            "      \"genre_ids\": [\n" +
            "        27\n" +
            "      ],\n" +
            "      \"id\": 431744,\n" +
            "      \"original_title\": \"Inside Me\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Inside Me\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001325,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"The full story of Elliot Rodger and his transformation into a twisted psychopath.\",\n" +
            "      \"release_date\": \"2023-09-12\",\n" +
            "      \"genre_ids\": [\n" +
            "        18\n" +
            "      ],\n" +
            "      \"id\": 431746,\n" +
            "      \"original_title\": \"The Supreme Gentleman\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"The Supreme Gentleman\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001344,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/ikblRaKBRNHkGAwny2804mmqXyF.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"\",\n" +
            "      \"release_date\": \"2023-03-01\",\n" +
            "      \"genre_ids\": [\n" +
            "        18\n" +
            "      ],\n" +
            "      \"id\": 435370,\n" +
            "      \"original_title\": \"Interplay\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Interplay\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.064877,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/2U1lagTa5nyaOcJdRpgpbnouSwf.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"The third sequel in the highly acclaimed Avatar series.\",\n" +
            "      \"release_date\": \"2022-12-01\",\n" +
            "      \"genre_ids\": [\n" +
            "        28,\n" +
            "        12,\n" +
            "        14,\n" +
            "        878\n" +
            "      ],\n" +
            "      \"id\": 216527,\n" +
            "      \"original_title\": \"Avatar 4\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Avatar 4\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.287624,\n" +
            "      \"vote_count\": 2,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"\",\n" +
            "      \"release_date\": \"2022-11-24\",\n" +
            "      \"genre_ids\": [\n" +
            "        \n" +
            "      ],\n" +
            "      \"id\": 422641,\n" +
            "      \"original_title\": \"Fantastic Beasts and Where to Find Them 4\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Fantastic Beasts and Where to Find Them 4\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.002844,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/p4hqCPEsQrSWAVTrReulXIVmHvC.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"During a reconnaissance flight on the north pole, a navy admiral finds himself in a fantastic world within the planet earth.\",\n" +
            "      \"release_date\": \"2022-06-10\",\n" +
            "      \"genre_ids\": [\n" +
            "        28,\n" +
            "        878,\n" +
            "        12\n" +
            "      ],\n" +
            "      \"id\": 392829,\n" +
            "      \"original_title\": \"The Realm of Agartha\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"The Realm of Agartha\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.002727,\n" +
            "      \"vote_count\": 1,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"Two cops are framed for murdering the president and must escape from prison in order to find out who set them up.\",\n" +
            "      \"release_date\": \"2022-03-25\",\n" +
            "      \"genre_ids\": [\n" +
            "        35,\n" +
            "        28\n" +
            "      ],\n" +
            "      \"id\": 431747,\n" +
            "      \"original_title\": \"Ready N' Reloaded\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Ready N' Reloaded\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001642,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/6nSbnHlLUiE1X8w7nkEuwn8zFO1.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"A small white creature travels across a sea of sand in a boat made of a discarded map after dreaming of an island shrine. He is carrying a precious white cloth pod sewn up with red stitching. On his journey, he meets a trio of gem-mining island-dwellers who are keen to have the pod for themselves. As he makes his escape from them, he encounters a ferocious blue sea monster that intends to drag him down under the sand to its lair. The island-dwellers remain in hot pursuit, crowded into a teacup, rowing with a silver spoon.\",\n" +
            "      \"release_date\": \"2021-12-31\",\n" +
            "      \"genre_ids\": [\n" +
            "        \n" +
            "      ],\n" +
            "      \"id\": 417778,\n" +
            "      \"original_title\": \"Seed in the Sand\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Seed in the Sand\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.002658,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"The world's most inescapable high tech security prison sits deep, deep, deep underneath Mount Baker. Suddenly the lights go off, sirens echo throughout the prison. The K-9 guards scramble for their flashlights. They move cautiously down the stairs to the only glass cage in the prison. It holds the world's most notorious jewel thief and mastermind villain. Or does it?\",\n" +
            "      \"release_date\": \"2021-12-07\",\n" +
            "      \"genre_ids\": [\n" +
            "        10751,\n" +
            "        16,\n" +
            "        12,\n" +
            "        35\n" +
            "      ],\n" +
            "      \"id\": 431820,\n" +
            "      \"original_title\": \"The Hamster\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"The Hamster\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001648,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"After the death of Alexander Smith, Mary Smith and James Anderson, born in the Holy of the Vineiia, had been heard happened, they are immediately to come back on the earth to help Beth Smith to destroy the Evil of the Dark before they are about to end Beth's life.\",\n" +
            "      \"release_date\": \"2021-11-19\",\n" +
            "      \"genre_ids\": [\n" +
            "        27,\n" +
            "        18\n" +
            "      ],\n" +
            "      \"id\": 431822,\n" +
            "      \"original_title\": \"Battle of Evil: The Evil of the Dark\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Battle of Evil: The Evil of the Dark\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001642,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/sTBbaXjUMidJKkjtKa9e2SdU0qM.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"China, 370 B.C. - After uncovering the mystical Luopan of Anatta, a Taoist monk journeys through history to find the two siblings that hold the key to protecting mankind from Armageddon.\",\n" +
            "      \"release_date\": \"2021-11-07\",\n" +
            "      \"genre_ids\": [\n" +
            "        10749,\n" +
            "        18,\n" +
            "        36\n" +
            "      ],\n" +
            "      \"id\": 431825,\n" +
            "      \"original_title\": \"The Chronicles of Anatta: Mark of Existence\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"The Chronicles of Anatta: Mark of Existence\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.002161,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/a5Iw7GrA8XLfN9TBFb37mNXuQMH.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"No overview found.\",\n" +
            "      \"release_date\": \"2021-07-17\",\n" +
            "      \"genre_ids\": [\n" +
            "        878,\n" +
            "        27\n" +
            "      ],\n" +
            "      \"id\": 29615,\n" +
            "      \"original_title\": \"It Came from Trafalgar\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"It Came from Trafalgar\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.002127,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"Presidential election and the impact on Americans.\",\n" +
            "      \"release_date\": \"2021-05-01\",\n" +
            "      \"genre_ids\": [\n" +
            "        99\n" +
            "      ],\n" +
            "      \"id\": 431935,\n" +
            "      \"original_title\": \"We the People 2016\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"We the People 2016\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001922,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": \"\\/4oEGCcluVHt12RoULEECijEDMZr.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"The trial of Lucifer.\",\n" +
            "      \"release_date\": \"2021-04-02\",\n" +
            "      \"genre_ids\": [\n" +
            "        53\n" +
            "      ],\n" +
            "      \"id\": 431936,\n" +
            "      \"original_title\": \"The Hunt Is Over\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"The Hunt Is Over\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001941,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"Tenth chapter of Fast &amp; Furious.\",\n" +
            "      \"release_date\": \"2021-04-01\",\n" +
            "      \"genre_ids\": [\n" +
            "        53,\n" +
            "        28,\n" +
            "        80\n" +
            "      ],\n" +
            "      \"id\": 385687,\n" +
            "      \"original_title\": \"Fast & Furious 10\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Fast & Furious 10\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 2.929966,\n" +
            "      \"vote_count\": 3,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"Plot is unknown.\",\n" +
            "      \"release_date\": \"2021-01-01\",\n" +
            "      \"genre_ids\": [\n" +
            "        12\n" +
            "      ],\n" +
            "      \"id\": 431940,\n" +
            "      \"original_title\": \"Karma 50 Climax\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Karma 50 Climax\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001941,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"Sean and Hank go on their biggest adventure yet, to the moon.\",\n" +
            "      \"release_date\": \"2020-12-31\",\n" +
            "      \"genre_ids\": [\n" +
            "        878,\n" +
            "        28,\n" +
            "        12,\n" +
            "        14\n" +
            "      ],\n" +
            "      \"id\": 181790,\n" +
            "      \"original_title\": \"Journey 3: From the Earth to the Moon\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"Journey 3: From the Earth to the Moon\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.992585,\n" +
            "      \"vote_count\": 5,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"poster_path\": null,\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"On December 31, 2020, the Swedish artist Anders Weberg will end his 20 plus years relation with the moving image as a means of creative expression. After more than 300 films, he will end with the premiere of what will be the longest film ever made. Ambiancé is 720 hours long (30 days) and will be shown in its full length on a single occasion, syncronised in all the continents of the world, and then destroyed. In the piece, space and time is intertwined into a surreal dream-like journey beyond places and is an abstract nonlinear narrative summary of the artist’s time spent with the moving image.\",\n" +
            "      \"release_date\": \"2020-12-31\",\n" +
            "      \"genre_ids\": [\n" +
            "        99\n" +
            "      ],\n" +
            "      \"id\": 280773,\n" +
            "      \"original_title\": \"Ambiancé\",\n" +
            "      \"original_language\": \"sv\",\n" +
            "      \"title\": \"Ambiancé\",\n" +
            "      \"backdrop_path\": null,\n" +
            "      \"popularity\": 1.001857,\n" +
            "      \"vote_count\": 0,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 0\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total_results\": 295595,\n" +
            "  \"total_pages\": 14780\n" +
            "}";
}
