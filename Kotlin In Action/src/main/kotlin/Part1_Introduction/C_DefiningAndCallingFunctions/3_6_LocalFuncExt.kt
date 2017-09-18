package Part1_Introduction.C_DefiningAndCallingFunctions

// local functions and extensions - dont repeat yourself, extract methods from
// large methods, or group them into inner classes

// kotlin has a clean solution, nest functions in the containing function

class User(val id:Int, val name:String, val address:String)

fun saveUser(user:User){
    if(user.name.isEmpty()) throw IllegalArgumentException("${user.id} name is empty")
    // field validation is duplicated
    if(user.address.isEmpty())throw IllegalArgumentException("${user.id} addr is empty")
    // save to db
}

fun doS(){
    saveUser(User(1, "", ""))
}

// lets move the validation code to a local function

// declares a local function to validate any field
fun saveUser1(user:User){

    fun validate(user:User, value:String, fieldName:String){
        if(value.isEmpty()) throw IllegalArgumentException("${user.id} $fieldName is empty")
    }

    // calls the local function to validate the specific fields
    validate(user, user.name, "Name")
    validate(user, user.address, "address")
}

// no need to pass teh user function, local funcs have access to all params of enclosing func

fun saveUser2(user:User){

    fun validate(value:String, fieldName:String){
        if(value.isEmpty()) throw IllegalArgumentException("${user.id} $fieldName is empty")
    }

    // calls the local function to validate the specific fields
    validate(user.name, "Name")
    validate(user.address, "address")
}

// better yet move the validation to the as an extension func in the user class

class User1(val id:Int, val name:String, val addres:String)

fun User1.validateBeforeSave(){
    fun validate(value:String, fieldName:String){
// access properties of the user directly
        if(value.isEmpty()) throw IllegalArgumentException("User${id}'s $fieldName is empty")
    }
}

fun saveUser3(user:User1) = user.validateBeforeSave() // wow, call extension func

// this prevents you from polluting your model class with custom validations, and
// allows for the flexibility of using user in other locations

// User.validateBeforeSave() can be a local function to saveUser(), but deeply nested local
// functions are hard to read, thus try for one level of nesting