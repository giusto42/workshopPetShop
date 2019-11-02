package petshop.workshop.com.persistence

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = TABLE_NAME, indices = [Index(value = ["name"], unique = true)])
data class Pet(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "age")
    val age: Int,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "details")
    val details: String,

    @ColumnInfo(name = "price")
    val price: Float,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String






) : Parcelable {

    companion object {
        fun dummyPetsData() = listOf(
            Pet(
                1,
                "Beagle",
                8,
                "Dog",
                "Prietenos, jucăuș și sociabil, acceptă ușor străinii și se împacă bine cu copiii.",
                100f,
                "https://4pede.ro/wp-content/uploads/2015/05/rasa-beagle.jpg"
            ),
            Pet(
                2,
                "Cavalier King Charles Spaniel",
                1,
                "Dog",
                "Cățelul e vioi, jucaus, prietenoș, inteligenț având o prezență elegantă, regală. Ideali ca si animale de companie pentru familie.",
                500f,
                "https://hellobark.com/wp-content/uploads/pixel-cavalier.jpg"
            ),
            Pet(
                3,
                "Husky",
                6,
                "Dog",
                "Husky Siberian este un câine nobil și prietenos, însă în același timp îndrăzneț și alert. Nu are sub nici o formă calitățile unui câine de pază și apărare, nu este suspicios față de străini și nici agresiv cu alți câini.",
                250f,
                "https://pbs.twimg.com/profile_images/639599645925076994/7Egv8qXQ.jpg"
            ),
            Pet(
                4,
                "Shiba Inu",
                4,
                "Dog",
                "A spirited boldness, a good nature, and an unaffected forthrightness, which together yield dignity and natural beauty. The Shiba has an independent nature and can be reserved toward strangers but is loyal and affectionate to those who earn his respect.",
                1000f,
                "https://gfp-2a3tnpzj.stackpathdns.com/wp-content/uploads/2016/07/Shiba-Inu-1600x700.jpg"
            ),
            Pet(
                5,
                "Australian Shepherd",
                2,
                "Dog",
                "are puternice instincte de turmă și îi place să ia parte la viața de familie, bucurându-se de compania stăpânilor săi. El poate fi inițial rezervat față de persoanele noi, astfel încât socializarea timpurie este esențială.",
                600f,
                "https://cutzucutzu.com/wp-content/uploads/2019/05/cea-mai-buna-hrana-ciobanesc-australian-2-1024x576.jpg"
            ),
            Pet(
                6,
                "Scottish Fold",
                1,
                "Cat",
                "Scottish Folds are good-natured and and adjust to other animals within a household extremely well. They tend to become very attached to their human caregivers and are by nature quite affectionate.",
                400f,
                "https://cupisici.ro/wp-content/uploads/2018/03/pisica-scottish-fold3.jpg"
            ),
            Pet(
                7,
                "Ragdoll",
                1,
                "Cat",
                "temperament docil și placid, au o natura afectuoasă.",
                500f,
                "https://www.thehappycatsite.com/wp-content/uploads/2018/06/ragdoll-cat-colors-header.jpg"
            ),
            Pet(
                8,
                "American Shorthair",
                8,
                "Cat",
                "American Shorthairs are low-maintenance cats that are generally healthy, easy-going, affectionate with owners and social with strangers.",
                450f,
                " https://scritch-production.imgix.net/b88a5750-137b-11e9-9aa0-05f277f78f75?w=1200&h=1200&fit=crop"
            ),
            Pet(
                9,
                "Snowshoe",
                1,
                "Cat",
                "Snowshoes are generally affectionate, sweet-tempered, and mellow. They enjoy the company of humans and being given attention, and are compatible with children and other pets.",
                500f,
                "https://img.pixers.pics/pho_wat(s3:700/FO/60/01/55/14/700_FO60015514_51d82b105f4ec1741270ea02d7f62c0a.jpg,551,700,cms:2018/10/5bd1b6b8d04b8_220x50-watermark.png,over,331,650,jpg)/stickers-chat-de-raquette-isole-sur-fond-blanc.jpg.jpg"
            ),
            Pet(
                10,
                "Exotic Shorthair",
                3,
                "Cat",
                "Pisica linistita, ce nu face tumbe si salturi gigant precum alte rase cu par scurt. De asemenea, este o pisica tacuta, ce arareori miauna.",
                350f,
                "https://thumbaymedia.com/petsplus/wp-content/uploads/2016/04/Exotic-Shorthair.jpg"
            )
        )
    }
}