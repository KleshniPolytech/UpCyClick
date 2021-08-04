package com.example.upcyclick

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.upcyclick.database.UpDB
import com.example.upcyclick.database.entity.Question
import com.example.upcyclick.database.entity.Scroll
import com.example.upcyclick.database.entity.Upgrade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AppSingleton private constructor(var context: Context) {

    var count: Int = 0

    var currentQuizDifficulty: Int = 0

    var upDB: UpDB? = null

    var updatesList: List<Upgrade> = mutableListOf()

    var upgradeCount: Int = 1

    init {
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        count = pref!!.getInt("Count", 0)

        CoroutineScope(Dispatchers.IO).launch {
            upDB = Room.databaseBuilder(context, UpDB::class.java, "UpDB").build()
            if (upDB?.scrollDao()?.getAll()?.isEmpty() == true) {
                fillUpgradeDB()
                fillScrollDB()
                fillQuestionDB()
            }

            updatesList = upDB!!.upgradeDao().getAcquiredUpdates()
            Log.d("LIST1", updatesList.size.toString())

            if(updatesList.isNotEmpty()){

                var size: Int? = getUpdateList().size
                upgradeCount = getUpdateList().get(size!!.minus(1)).income
                Log.d("LIST2", upgradeCount.toString())
            }

            this.cancel()
        }
    }

    fun updateUpgradeCount() {

        var size: Int? = getUpdateList().size
        upgradeCount = getUpdateList()[size!!.minus(1)].income

    }

    private fun getUpdateList(): List<Upgrade> {
        CoroutineScope(Dispatchers.IO).launch {
            updatesList = upDB!!.upgradeDao().getAcquiredUpdates()
            Log.d("LIST3", updatesList.size.toString())
        }

        return updatesList
    }

    private fun fillUpgradeDB() {
        upDB?.upgradeDao()?.insert(
            Upgrade(
                "UpCy Double Click",
                false,
                2

            ),
            Upgrade(
                "UpCy Triple Click",
                false,
                3

            ),
            Upgrade(
                "UpCy Quadr Click",
                false,
                4

            ),
            Upgrade(
                "UpCy Mega Click",
                false,
                5

            ),


            )
    }

    private fun fillQuestionDB() {

        upDB?.questionDao()?.insert(
            Question(
                1,
                "Food waste reduction is one of the most important things individuals can do to help reverse global warming.",
                "True|False",
                "True",
                "Food waste is a massive environmental issue. Food that is never eaten or used still has environmental costs — from the land and resources used to make the food to the transporting of ingredients to stores. We can all do our part to reduce our food waste to help the planet.",
                R.drawable.q1
            ),
            Question(
                1,
                "How much of the world’s food is never eaten, which means that the food's production wasted land and resources and emitted unnecessary greenhouse gases?",
                "10%|25%|33%|50%",
                "25%",
                "Food is wasted from more than what you throw away at home. Crops can be thrown out due to weather, low market prices, or high labor costs. Grocery stores will often overstock their shelves and must throw out excess product as well.",
                R.drawable.q2
            ),
            Question(
                1,
                "Of the estimated 1 billion tons of food that goes to waste every year, much of it is perfectly edible and nutritious.",
                "True|False",
                "True",
                "Criteria for acceptable - or “market-grade” - food often relies on how good it looks, not on its nutrition or safety for people to eat.",
                R.drawable.q3
            ),
            Question(
                1,
                "Upcycled food is all about elevating food to its:",
                "Least expensive use|Highest and best use|Most popular use|Use as pet food",
                "Highest and best use",
                "Upcycling food is an example of a “value-added product,” like turning flour into bread, but also captures the value of upcycled ingredients and saves them from being wasted.",
                R.drawable.q4
            ),
            Question(
                1,
                "What is an “upcycled food?”",
                "A food that has gone slightly ‘off’ but can still be used to make another food; for example, over-ripe apples can be used in applesauce.|A food that’s been changed from a basic ingredient to a value-added food; for example, a potato that’s been turned into a potato chip.|Food that’s been creatively saved from becoming food waste and put to its best and highest use; for example, the husk of a coffee bean, called a ‘coffee cherry,’ that’s used to make flour for cookies.|A food that was rescued from turning into waste after it reached its expiration date; for example, milk that’s still good but has gone a few days past the stamped “best by” date.",
                "Food that’s been creatively saved from becoming food waste and put to its best and highest use; for example, the husk of a coffee bean, called a ‘coffee cherry,’ that’s used to make flour for cookies.",
                "Upcycling food is inherently sustainable, as it makes the most out of each piece of food. Upcycling is able to do more with less!",
                R.drawable.q5
            ),
            Question(
                1,
                "Bananas that are just a few centimeters too short, or a few days too ripe, should be discarded before shipping because they are unusable and will waste resources in transit.",
                "True|False",
                "False",
                "Bananas that don’t qualify to be exported because they’re too small or too ripe are great candidates for upcycling into delicious snacks like chewy bites or cookie brittle!",
                R.drawable.q6
            ),
            Question(
                1,
                "Which of the following resources are wasted or lost when food that is produced goes uneaten? (Select the ONE best answer.)",
                "Financial capital|None of the above|Fertilizer|Energy",
                "All of the above",
                "Food waste wastes more than food! Upcycling food saves resources by giving a new purpose to ingredients that would otherwise get thrown away.",
                R.drawable.q7
            ),
            Question(
                1,
                "The Upcycled Food Association estimates that as of January 2021, there are already ___ upcycled products sold in the United States:",
                "Financial capital|None of the above|Fertilizer|Energy",
                "All of the above",
                "Food waste wastes more than food! Upcycling food saves resources by giving a new purpose to ingredients that would otherwise get thrown away.",
                R.drawable.q7
            ),
            Question(
                1,
                "Which of the following resources are wasted or lost when food that is produced goes uneaten? (Select the ONE best answer.)",
                "175|400|250|625",
                "400",
                "Upcycling is a no-brainer! Groups around the globe are finding new ways to reimagine our global food production to make sure our resources are used more efficiently and our environment is protected in the process.",
                R.drawable.q8
            ),
            Question(
                1,
                "Upcycled foods can help (select the ONE best answer):",
                "Feed a growing population|Reduce greenhouse gas emission|Save our forests|All of the above",
                "All of the above",
                "Upcycling Food means more than tasty and nutritious foods. The supply chain process directly benefits the natural environment by making the most out of our farmland and its resources.",
                R.drawable.q9
            ),
            Question(
                1,
                "If extracts from an avocado pit are used as natural dyes for clothing, the avocado pit was officially 'upcycled.'",
                "True|False",
                "True",
                "This one is a little tricky! Upcycled foods are for human consumption, but upcycled ingredients could be included in animal feed, pet food, cosmetics, clothing and more.",
                R.drawable.q10
            ),
            Question(
                1,
                "When foods like bananas are upcycled, small, local farmers benefit by selling more of their crop and getting paid fairly for their labor.",
                "True|False",
                "True",
                "Small farmers often struggle to compete with major producers, and are especially vulnerable to changes in the value of their crops. Upcycling foods opens new markets for local farmers to sell their excess product.",
                R.drawable.q11
            ),
            Question(
                1,
                "Upcycling foods creates air and water pollution.",
                "True|False",
                "False",
                "Just the opposite! Upcycling foods reduces air and water pollution.",
                R.drawable.q12
            ),
            Question(
                1,
                "As an individual, I can do my part to help reduce food waste by (select the ONE best answer):",
                "Learning more about upcycled foods|Supporting brands that use upcycled ingredients|Planning my meals so I know how much I need at the store|Using a tool like a Foodprint Calculator to understand the impact of my food choices",
                "All of the above",
                "Fight climate change with diet change! Your food choices make an impact on our global environment. There is not one perfect diet for everyone, but we can all do our part to make the relationship with food we eat more sustainable!",
                R.drawable.q13
            ),
            Question(
                1,
                "Upcycling is",
                "turning trash into valuable objects|another name for recycling|turning trash into something less valuable|none of the above",
                "turning trash into valuable objects",
                "", //TODO
                R.drawable.q13 //TODO
            ),

            )

    }

    private fun fillScrollDB() {
        upDB?.scrollDao()?.insert(
            Scroll(
                "Baby food pouch",
                "Baby_Food_Pouch_Bib_Instructions(Leg).pdf",
                false,
                "Don’t mess around, upcycle your dirty baby food pouches into a sweet new bib!",
                3,
            ),
            Scroll(
                "Cosmetic Tube Earrings",
                "Cosmetic_Tube_Earrings_Instructions(Rare).pdf",
                false,
                "Unbottle your creativity and create upcycled earrings! ",
                2,
            ),
            Scroll(
                "Designer Collection Bin",
                "Designer_Collection_Bin_Instructions(Com).pdf",
                false,
                "This trash “can” be turned into a designer styled collection bin!",
                1,
            ),
            Scroll(
                "Fancy GiftBow",
                "Fancy_GiftBow_Instructions(Rare).pdf",
                false,
                "Rewrap your gifts with upcycled food wrappers!",
                2,
            ),
            Scroll(
                "Malt-O-Meal Pinwheel",
                "Malt-O-Meal_Pinwheel_DIY_Instructions(Com).pdf",
                false,
                "Put a new spin on a classic pinwheel with an upcycled cereal bag! ",
                1,
            ),
            Scroll(
                "Origami_Flower",
                "MOM_Brands_Origami_Flower_Bouquet(Rare).pdf",
                false,
                "Dress things up with this sweet origami project!",
                2,
            ),
            Scroll(
                "Nespresso Capsule Bracelet",
                "Nespresso_Capsule_Bracelet_Instructions(Rare).pdf",
                false,
                "Create a TerraCycle bracelet from Nespresso capsules!",
                2,
            ),
            Scroll(
                "Nespresso Coffee Cup Sleeve",
                "Nespresso_Coffee_Cup_Sleeve_Instructions(Com).pdf",
                false,
                "Make a hot coffee cup sleeve to keep your hands cool! ",
                1,
            ),
            Scroll(
                "Pallet Table",
                "Pallet_Table_DIY-v1-us(Leg).pdf",
                false,
                "Clean up an old pallet and make yourself a new upcycled coffee table!",
                3,
            ),
            Scroll(
                "Pen Lamp Shade",
                "Pen_Lamp_Shade_DIY_Instructions(Leg).pdf",
                false,
                "Transform your tired old lampshade into a crafty new table lamp!",
                3,
            ),
            Scroll(
                "Shampoo Bottle Chandelier",
                "Shampoo_Bottle_Chandelier_Instructions(Leg).pdf",
                false,
                "Create a TerraCycle chandelier from shampoo and conditioner bottles! ",
                3,
            ),
            Scroll(
                "Single Drink Pouch",
                "Single_Drink_Pouch_Wallet_DIY(Com).pdf",
                false,
                "Create a funky, upcycled wallet to show off to your friends",
                1,
            )

        )
    }

    companion object : SingletonHolder<AppSingleton, Context>(::AppSingleton)
}