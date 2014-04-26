jQuery.cookie = function(a, b, c) {
    if (typeof b == "undefined") {
        var i = null;
        if (document.cookie && document.cookie != "") {
            var j = document.cookie.split(";");
            for (var k = 0; k < j.length; k++) {
                var l = jQuery.trim(j[k]);
                if (l.substring(0, a.length + 1) == a + "=") {
                    i = decodeURIComponent(l.substring(a.length + 1));
                    break
                }
            }
        }
        return i
    }
    c = c || {},
    b === null && (b = "", c.expires = -1);
    var d = "";
    if (c.expires && (typeof c.expires == "number" || c.expires.toUTCString)) {
        var e;
        typeof c.expires == "number" ? (e = new Date, e.setTime(e.getTime() + c.expires * 24 * 60 * 60 * 1e3)) : e = c.expires,
        d = "; expires=" + e.toUTCString()
    }
    var f = c.path ? "; path=" + c.path: "",
    g = c.domain ? "; domain=" + c.domain: "",
    h = c.secure ? "; secure": "";
    document.cookie = [a, "=", encodeURIComponent(b), d, f, g, h].join("")
},
function(a) {
    function d(a) {
        var d = b[a.which],
        e,
        f = "";
        a.ctrlKey && d !== "ctrl" && (f += "ctrl+"),
        a.altKey && d !== "alt" && (f += "alt+"),
        a.metaKey && !a.ctrlKey && d !== "meta" && (f += "meta+");
        return a.shiftKey ? (e = c[a.which]) ? "" + f + e: d === "shift" ? "" + f + "shift": d ? "" + f + "shift+" + d: null: d ? "" + f + d: null
    }
    var b = {
        8 : "backspace",
        9 : "tab",
        13 : "enter",
        16 : "shift",
        17 : "ctrl",
        18 : "alt",
        19 : "pause",
        20 : "capslock",
        27 : "esc",
        32 : "space",
        33 : "pageup",
        34 : "pagedown",
        35 : "end",
        36 : "home",
        37 : "left",
        38 : "up",
        39 : "right",
        40 : "down",
        45 : "insert",
        46 : "del",
        48 : "0",
        49 : "1",
        50 : "2",
        51 : "3",
        52 : "4",
        53 : "5",
        54 : "6",
        55 : "7",
        56 : "8",
        57 : "9",
        65 : "a",
        66 : "b",
        67 : "c",
        68 : "d",
        69 : "e",
        70 : "f",
        71 : "g",
        72 : "h",
        73 : "i",
        74 : "j",
        75 : "k",
        76 : "l",
        77 : "m",
        78 : "n",
        79 : "o",
        80 : "p",
        81 : "q",
        82 : "r",
        83 : "s",
        84 : "t",
        85 : "u",
        86 : "v",
        87 : "w",
        88 : "x",
        89 : "y",
        90 : "z",
        91 : "meta",
        93 : "meta",
        96 : "0",
        97 : "1",
        98 : "2",
        99 : "3",
        100 : "4",
        101 : "5",
        102 : "6",
        103 : "7",
        104 : "8",
        105 : "9",
        106 : "*",
        107 : "+",
        109 : "-",
        110 : ".",
        111 : "/",
        112 : "f1",
        113 : "f2",
        114 : "f3",
        115 : "f4",
        116 : "f5",
        117 : "f6",
        118 : "f7",
        119 : "f8",
        120 : "f9",
        121 : "f10",
        122 : "f11",
        123 : "f12",
        144 : "numlock",
        145 : "scroll",
        186 : ";",
        187 : "=",
        188 : ",",
        189 : "-",
        190 : ".",
        191 : "/",
        192 : "`",
        219 : "[",
        220 : "\\",
        221 : "]",
        222 : "'"
    },
    c = {
        48 : ")",
        49 : "!",
        50 : "@",
        51 : "#",
        52 : "$",
        53 : "%",
        54 : "^",
        55 : "&",
        56 : "*",
        57 : "(",
        65 : "A",
        66 : "B",
        67 : "C",
        68 : "D",
        69 : "E",
        70 : "F",
        71 : "G",
        72 : "H",
        73 : "I",
        74 : "J",
        75 : "K",
        76 : "L",
        77 : "M",
        78 : "N",
        79 : "O",
        80 : "P",
        81 : "Q",
        82 : "R",
        83 : "S",
        84 : "T",
        85 : "U",
        86 : "V",
        87 : "W",
        88 : "X",
        89 : "Y",
        90 : "Z",
        186 : ":",
        187 : "+",
        188 : "<",
        189 : "_",
        190 : ">",
        191 : "?",
        192 : "~",
        219 : "{",
        220 : "|",
        221 : "}",
        222 : '"'
    };
    a.browser.mozilla && (c[0] = "?"),
    a.each(["keydown", "keyup", "keypress"],
    function() {
        a.event.special[this] = {
            add: function(b) {
                if (typeof b.data == "string") {
                    var c = b.handler,
                    e = b.data;
                    b.handler = function(b) {
                        if (this === b.target || !a(b.target).is(":input")) if (e === d(b)) return c.apply(this, arguments)
                    }
                }
            }
        }
    }),
    globalMappings = {},
    a(document).bind("keydown.hotkey",
    function(b) {
        if (!a(b.target).is(":input")) {
            var c = d(b),
            e = globalMappings[c];
            if (c && e) return e.apply(this, arguments)
        }
    }),
    a.hotkeys = function(a) {
        for (key in a) globalMappings[key] = a[key];
        return this
    },
    a.hotkey = function(a, b) {
        globalMappings[a] = b;
        return this
    }
} (jQuery),
function(a) {
    var b = [],
    c = a(window).width(),
    d = a(window).height();
    a(window).resize(function() {
        c = a(window).width(),
        d = a(window).height()
    }),
    a.fn.plaxify = function(a) {
        var c = {
            xRange: 0,
            yRange: 0,
            invert: !1
        },
        d = this.position();
        for (var e in a) c[e] == 0 && (c[e] = a[e]);
        c.obj = this,
        c.startX = d.left,
        c.startY = d.top,
        c.invert == !1 ? (c.startX -= Math.floor(c.xRange / 2), c.startY -= Math.floor(c.yRange / 2)) : (c.startX += Math.floor(c.xRange / 2), c.startY += Math.floor(c.yRange / 2)),
        b.push(c)
    },
    a.plax = {
        enable: function() {
            a(document).mousemove(function(e) {
                var f = e.pageX,
                g = e.pageY,
                h = Math.round(f / c * 100) / 100,
                i = Math.round(g / d * 100) / 100;
                a.each(b,
                function(a, b) {
                    b.invert != !0 ? (b.obj.css("left", b.startX + b.xRange * h), b.obj.css("top", b.startY + b.yRange * i)) : (b.obj.css("left", b.startX - b.xRange * h), b.obj.css("top", b.startY - b.yRange * i))
                })
            })
        }
    }
} (jQuery)