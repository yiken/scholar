$(function() {
    $('[type="radio"]').each(function(index, obj) {
        var $this = $(this);
        var controlName = $this.attr('name');
        var siblingControls = $this.parent().siblings();
        $this.attr('disabled', 'disabled');
        var disabled = $this.data('disabled') || false;
        var checked = $this.attr('checked') || false;

        $this.addClass('cbr cbr-done');

        var wrapper = $('<div>', {
            'class': 'cbr-replaced cbr-radio cbr-' + ($this.data('type') ? $this.data('type') : 'default') + (disabled ? ' cbr-disabled' : '') + (checked ? ' cbr-checked' : '')
        }).prependTo($this.parent());

        var cbr_input = $('<div>', {
            'class': 'cbr-input'
        }).append($this).appendTo(wrapper);

        var cbr_state = $('<div>', {
            'class': 'cbr-state'
        }).append($('<span>', {})).appendTo(wrapper);
        wrapper.parent().click(function(event) {
            if (wrapper.hasClass('cbr-disabled')) {
                return false;
            }
            if (!wrapper.hasClass('cbr-checked')) {
                wrapper.addClass('cbr-checked');
                $this.prop('checked', true);
                siblingControls.find('.cbr-radio.cbr-checked').removeClass('cbr-checked');
            }
        }).css({
            cursor: 'pointer'
        });
    });

    $('[type="checkbox"]').each(function(index, obj) {
        var $this = $(this);
        var controlName = $this.attr('name');
        var siblingControls = $this.parent().siblings();
        $this.attr('disabled', 'disabled');
        var disabled = $this.data('disabled') || false;
        var checked = $this.attr('checked') || false;

        $this.addClass('cbr cbr-done');

        var wrapper = $('<div>', {
            'class': 'cbr-replaced cbr-checkbox cbr-' + ($this.data('type') ? $this.data('type') : 'default') + (disabled ? ' cbr-disabled' : '') + (checked ? ' cbr-checked' : '')
        }).prependTo($this.parent());

        var cbr_input = $('<div>', {
            'class': 'cbr-input'
        }).append($this).appendTo(wrapper);

        var cbr_state = $('<div>', {
            'class': 'cbr-state'
        }).append($('<span>', {})).appendTo(wrapper);
        wrapper.parent().click(function(event) {
            if (wrapper.hasClass('cbr-disabled')) {
                return false;
            }
            wrapper.toggleClass('cbr-checked');
            if (wrapper.hasClass('cbr-checked')) {
                $this.prop('checked', true);
            } else {
                $this.prop('checked', false);
            }
        }).css({
            cursor: 'pointer'
        });
    });
})