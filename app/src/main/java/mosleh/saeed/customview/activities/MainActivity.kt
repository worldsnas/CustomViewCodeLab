package mosleh.saeed.customview.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import mosleh.saeed.customview.R
import mosleh.saeed.customview.ViewModels.MainActivityViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private  val viewModel: MainActivityViewModel by lazy { ViewModelProviders.of(this).get(MainActivityViewModel::class.java) }

val rand = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)


        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            indicator.setSelectedPage(rand.nextInt(5))
//            indicator.setPageAnimated(rand.nextInt(5))
        }
    }


}
