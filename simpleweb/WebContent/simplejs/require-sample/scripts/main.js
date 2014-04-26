require.config({
    paths: {
        jquery: "../../../jslib/jquery-1.7.1"
    }
});

require(["jquery", "jquery.alpha", "jquery.beta"], function($) {
    //the jquery.alpha.js and jquery.beta.js plugins have been loaded.
    $(function() {
        $('body').alpha().beta();
    });
});
