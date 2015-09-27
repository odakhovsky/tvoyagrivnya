/*!
 * jquery.confirm
 *
 * @version 2.1.0
 *
 * @author My C-Labs
 * @author Matthieu Napoli <matthieu@mnapoli.fr>
 * @author Russel Vela
 *
 * @license MIT
 * @url http://myclabs.github.io/jquery.confirm/
 */
(function ($) {

    /**
     * Confirm a link or a button
     * @param options {title, text, confirm, cancel, confirmButton, cancelButton, post}
     */
    $.fn.alert = function (options) {
        if (typeof options === 'undefined') {
            options = {};
        }

        this.click(function (e) {
            e.preventDefault();

            var newOptions = $.extend({
                button: $(this)
            }, options);

            $.alert(newOptions, e);
        });

        return this;
    };

    /**
     * Show a confirmation dialog
     * @param options {title, text, confirm, cancel, confirmButton, cancelButton, post}
     */
    $.alert = function (options, e) {
        // Default options
        var settings = $.extend($.alert.options, {
            alert: function (o) {
                var url = e && (('string' === typeof e && e) || (e.currentTarget && e.currentTarget.attributes['href'].value));
                if (url) {
                    if (options.post) {
                        var form = $('<form method="post" class="hide" action="' + url + '"></form>');
                        $("body").append(form);
                        form.submit();
                    } else {
                        window.location = url;
                    }
                }
            },
            cancel: function (o) {
            },
            button: null
        }, options);

        // Modal
        var modalHeader = '';
        if (settings.title !== '') {
            modalHeader =
                '<div class=modal-header>' +
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                '<h4 class="modal-title">' + settings.title+'</h4>' +
                '</div>';
        }

        if(!settings.autoClosable) {
            var modalFooterAndButtonOk =
                '<div class="modal-footer">' +
                    '<button class="confirm btn btn-primary" type="button" data-dismiss="modal">' +
                    settings.confirmButton +
                    '</button>' +
                '</div>';
        }else modalFooterAndButtonOk = '';

        var modalHTML =
            '<div class="confirmation-modal modal fade" tabindex="-1" role="dialog">' +
                '<div class="modal-dialog">' +
                    '<div class="modal-content">' +
                    modalHeader +
                        '<div class="modal-body">' + settings.text + '</div>' +
                            modalFooterAndButtonOk +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>';

        var modal = $(modalHTML);

        modal.on('shown', function () {
            modal.find(".btn-primary:first").focus();
        });
        modal.on('hidden', function () {
            modal.remove();
        });
        modal.find(".confirm").click(function () {
            settings.alert(settings.button);
        });

        // Show the modal
        $("body").append(modal);
        modal.modal('show');

        if(settings.autoClosable){
            setTimeout(function () {
                modal.modal('hide');
            }, 1000);
        }
    };

    /**
     * Globally definable rules
     * @type {{text: string, title: string, confirmButton: string, cancelButton: string, post: boolean, confirm: Function, cancel: Function, button: null}}
     */
    $.alert.options = {
        text: "",
        title: "",
        confirmButton: "Ok",
        post: false,
        autoClosable: false
    }
})(jQuery);
