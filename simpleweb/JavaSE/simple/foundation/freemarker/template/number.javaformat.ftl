<#assign x = 1511.234>
${x?string("0")}
${x?string("0.#")}
${x?string("0.##")}
${x?string("0.###")}
${x?string("0.####")}

${1?string("000.00")}
${12.1?string("000.00")}
${123.456?string("000.00")}

${1.2?string("0")}
${1.8?string("0")}
${1.5?string("0")} <-- 1.5, rounded towards even neighbor
${2.5?string("0")} <-- 2.5, rounded towards even neighbor

${12345?string("0.##E0")}  