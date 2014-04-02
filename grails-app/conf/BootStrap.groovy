
import tisseo.Places

class BootStrap {

    def init = { servletContext ->
		
		new Places().save()
    }
    def destroy = {
    }
}
