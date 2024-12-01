package domain.entity

class Assento (
    val nome: String,
    var ocupado: Boolean = false){

    fun ocupar() {
        this.ocupado = true;
    }

    fun desocupar(){
        this.ocupado = false;
    }
}