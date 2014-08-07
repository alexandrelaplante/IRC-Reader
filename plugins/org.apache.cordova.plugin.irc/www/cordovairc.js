
var exec = require("cordova/exec");

var irc = {
    connect: function (callback, args) {
        exec(callback, function(err){alert(err);},'CordovaIrc', 'connect', args);
    },

    send: function (args) {
        exec(function(res){}, function(err){alert(err);},'CordovaIrc', 'send', args);
    },
}

module.exports = irc;