package mosleh.saeed.states



import java.util.ArrayList


sealed class MainActivityState {
    class Success(params : ArrayList<String>) : MainActivityState()
    class Error(errorID: Int) : MainActivityState()
    class ShowToast(message : String) : MainActivityState()
}
