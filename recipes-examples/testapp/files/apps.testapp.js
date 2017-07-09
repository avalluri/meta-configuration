L.ui.view.extend({
    title: L.tr('My Awsome Application'),
    description: L.tr('A test application to demonstrate LuCI usage'),

    execute: function() {
       var m = new L.cbi.Map('testapp', {
           caption: L.tr('Caption'),
           description: L.tr('Description')
       });

       var s = m.section(L.cbi.DummySection, 'main', {
           caption: L.tr('Section name'),
           description: L.tr('Section description'),
       });

       p = s.option(L.cbi.InputValue, 'User', {
           caption: L.tr('Input username:'),
           description: L.tr('Username'),
           datatype: 'string',
           placeholder: 'Default Value',
           optional: false,
           readonly: false,
       });

       p = s.option(L.cbi.InputValue, 'Group', {
           caption: L.tr('Input group name:'),
           description: L.tr('Group name'),
           datatype: 'string',
           placeholder: 'Default Value',
           optional: false,
           readonly: false,
       });
       return m.insertInto("#content");
    }
});
