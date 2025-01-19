const baseUrl = 'https://servicodados.ibge.gov.br/api/v1/localidades'

export const getLocations = async (type: 'estados' | 'distritos') => {
    const response = await fetch(`${baseUrl}/${type}`);
    return response.json();
}
