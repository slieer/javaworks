<#setting number_format="currency">
<#assign x=42.023>
${x}
${x?string}  <#-- the same as ${x} -->
${x?string.number}
${x?string.currency}
${x?string.percent}