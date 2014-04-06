package tisseo



import org.junit.*
import grails.test.mixin.*

@TestFor(LineController)
@Mock(Line)
class LineControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/line/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.lineInstanceList.size() == 0
        assert model.lineInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.lineInstance != null
    }

    void testSave() {
        controller.save()

        assert model.lineInstance != null
        assert view == '/line/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/line/show/1'
        assert controller.flash.message != null
        assert Line.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/line/list'

        populateValidParams(params)
        def line = new Line(params)

        assert line.save() != null

        params.id = line.lineId

        def model = controller.show()

        assert model.lineInstance == line
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/line/list'

        populateValidParams(params)
        def line = new Line(params)

        assert line.save() != null

        params.id = line.lineId

        def model = controller.edit()

        assert model.lineInstance == line
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/line/list'

        response.reset()

        populateValidParams(params)
        def line = new Line(params)

        assert line.save() != null

        // test invalid parameters in update
        params.id = line.lineId
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/line/edit"
        assert model.lineInstance != null

        line.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/line/show/$line.lineId"
        assert flash.message != null

        //test outdated version number
        response.reset()
        line.clearErrors()

        populateValidParams(params)
        params.id = line.lineId
        params.version = -1
        controller.update()

        assert view == "/line/edit"
        assert model.lineInstance != null
        assert model.lineInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/line/list'

        response.reset()

        populateValidParams(params)
        def line = new Line(params)

        assert line.save() != null
        assert Line.count() == 1

        params.id = line.lineId

        controller.delete()

        assert Line.count() == 0
        assert Line.get(line.lineId) == null
        assert response.redirectedUrl == '/line/list'
    }
}
