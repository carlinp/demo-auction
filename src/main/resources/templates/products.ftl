<#list products as product>
    Id: ${product.id}
    Name: ${product.name!""}
    Description ${product.description!""}
</#list>
