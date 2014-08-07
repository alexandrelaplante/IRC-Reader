cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/org.apache.cordova.plugin.tts/www/tts.js",
        "id": "org.apache.cordova.plugin.tts.tts",
        "clobbers": [
            "navigator.tts"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.plugin.irc/www/cordovairc.js",
        "id": "org.apache.cordova.plugin.irc.cordovairc",
        "clobbers": [
            "window.plugins.irc"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "org.apache.cordova.plugin.tts": "0.2.0",
    "org.apache.cordova.plugin.irc": "0.2.0"
}
// BOTTOM OF METADATA
});