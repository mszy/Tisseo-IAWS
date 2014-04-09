package velo

import org.springframework.dao.DataIntegrityViolationException

class LineVeloController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [lineVeloInstanceList: LineVelo.list(params), lineVeloInstanceTotal: LineVelo.count()]
    }

    def create() {
        [lineVeloInstance: new LineVelo(params)]
    }

    def save() {
        def lineVeloInstance = new LineVelo(params)
        if (!lineVeloInstance.save(flush: true)) {
            render(view: "create", model: [lineVeloInstance: lineVeloInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'lineVelo.label', default: 'LineVelo'), lineVeloInstance.id])
        redirect(action: "show", id: lineVeloInstance.id)
    }

    def show(Long id) {
        def lineVeloInstance = LineVelo.get(id)
        if (!lineVeloInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lineVelo.label', default: 'LineVelo'), id])
            redirect(action: "list")
            return
        }

        [lineVeloInstance: lineVeloInstance]
    }

    def edit(Long id) {
        def lineVeloInstance = LineVelo.get(id)
        if (!lineVeloInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lineVelo.label', default: 'LineVelo'), id])
            redirect(action: "list")
            return
        }

        [lineVeloInstance: lineVeloInstance]
    }

    def update(Long id, Long version) {
        def lineVeloInstance = LineVelo.get(id)
        if (!lineVeloInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lineVelo.label', default: 'LineVelo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (lineVeloInstance.version > version) {
                lineVeloInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'lineVelo.label', default: 'LineVelo')] as Object[],
                          "Another user has updated this LineVelo while you were editing")
                render(view: "edit", model: [lineVeloInstance: lineVeloInstance])
                return
            }
        }

        lineVeloInstance.properties = params

        if (!lineVeloInstance.save(flush: true)) {
            render(view: "edit", model: [lineVeloInstance: lineVeloInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'lineVelo.label', default: 'LineVelo'), lineVeloInstance.id])
        redirect(action: "show", id: lineVeloInstance.id)
    }

    def delete(Long id) {
        def lineVeloInstance = LineVelo.get(id)
        if (!lineVeloInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lineVelo.label', default: 'LineVelo'), id])
            redirect(action: "list")
            return
        }

        try {
            lineVeloInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'lineVelo.label', default: 'LineVelo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'lineVelo.label', default: 'LineVelo'), id])
            redirect(action: "show", id: id)
        }
    }
}
