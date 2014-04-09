package velo



import org.junit.*
import grails.test.mixin.*

@TestFor(LineVeloController)
@Mock(LineVelo)
class LineVeloControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/lineVelo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.lineVeloInstanceList.size() == 0
        assert model.lineVeloInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.lineVeloInstance != null
    }

    void testSave() {
        controller.save()

        assert model.lineVeloInstance != null
        assert view == '/lineVelo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/lineVelo/show/1'
        assert controller.flash.message != null
        assert LineVelo.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/lineVelo/list'

        populateValidParams(params)
        def lineVelo = new LineVelo(params)

        assert lineVelo.save() != null

        params.id = lineVelo.id

        def model = controller.show()

        assert model.lineVeloInstance == lineVelo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/lineVelo/list'

        populateValidParams(params)
        def lineVelo = new LineVelo(params)

        assert lineVelo.save() != null

        params.id = lineVelo.id

        def model = controller.edit()

        assert model.lineVeloInstance == lineVelo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/lineVelo/list'

        response.reset()

        populateValidParams(params)
        def lineVelo = new LineVelo(params)

        assert lineVelo.save() != null

        // test invalid parameters in update
        params.id = lineVelo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/lineVelo/edit"
        assert model.lineVeloInstance != null

        lineVelo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/lineVelo/show/$lineVelo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        lineVelo.clearErrors()

        populateValidParams(params)
        params.id = lineVelo.id
        params.version = -1
        controller.update()

        assert view == "/lineVelo/edit"
        assert model.lineVeloInstance != null
        assert model.lineVeloInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/lineVelo/list'

        response.reset()

        populateValidParams(params)
        def lineVelo = new LineVelo(params)

        assert lineVelo.save() != null
        assert LineVelo.count() == 1

        params.id = lineVelo.id

        controller.delete()

        assert LineVelo.count() == 0
        assert LineVelo.get(lineVelo.id) == null
        assert response.redirectedUrl == '/lineVelo/list'
    }
}
