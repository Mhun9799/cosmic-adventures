import{d as $,j as ee,e as te,g as le,k as ne,o as ie,f as ae,l as L,p as oe,a as I,Q as x,c as ue}from"./QCard.749dc18c.js";import{b as re,a as se,Q as de}from"./axios.78b7d72a.js";import{r as B,c as f,g as X,C as ce,E as G,Y as me,h as V,S as fe,F as pe,s as M,t as W,u as S,v as c,a7 as ve,K as ge}from"./index.bced2722.js";import{c as he}from"./render.74fb01ec.js";import{Q as Y}from"./QBtn.82ccdb52.js";import{Q as be}from"./QPage.70d7e392.js";import{_ as Ve}from"./plugin-vue_export-helper.21dcd24c.js";import"./use-router-link.168c0bc2.js";import"./use-dark.73534cf9.js";import"./QItem.ae2353e6.js";import"./scroll.393b85a0.js";function C(e,n,F,b){const l=[];return e.forEach(s=>{b(s)===!0?l.push(s):n.push({failedPropValidation:F,file:s})}),l}function A(e){e&&e.dataTransfer&&(e.dataTransfer.dropEffect="copy"),G(e)}const Fe={multiple:Boolean,accept:String,capture:String,maxFileSize:[Number,String],maxTotalSize:[Number,String],maxFiles:[Number,String],filter:Function},ye=["rejected"];function xe({editable:e,dnd:n,getFileInput:F,addFilesToQueue:b}){const{props:l,emit:s,proxy:u}=X(),y=B(null),w=f(()=>l.accept!==void 0?l.accept.split(",").map(t=>(t=t.trim(),t==="*"?"*/":(t.endsWith("/*")&&(t=t.slice(0,t.length-1)),t.toUpperCase()))):null),k=f(()=>parseInt(l.maxFiles,10)),z=f(()=>parseInt(l.maxTotalSize,10));function U(t){if(e.value)if(t!==Object(t)&&(t={target:null}),t.target!==null&&t.target.matches('input[type="file"]')===!0)t.clientX===0&&t.clientY===0&&ce(t);else{const v=F();v&&v!==t.target&&v.click(t)}}function D(t){e.value&&t&&b(null,t)}function Q(t,v,P,N){let a=Array.from(v||t.target.files);const p=[],h=()=>{p.length!==0&&s("rejected",p)};if(l.accept!==void 0&&w.value.indexOf("*/")===-1&&(a=C(a,p,"accept",o=>w.value.some(d=>o.type.toUpperCase().startsWith(d)||o.name.toUpperCase().endsWith(d))),a.length===0))return h();if(l.maxFileSize!==void 0){const o=parseInt(l.maxFileSize,10);if(a=C(a,p,"max-file-size",d=>d.size<=o),a.length===0)return h()}if(l.multiple!==!0&&a.length!==0&&(a=[a[0]]),a.forEach(o=>{o.__key=o.webkitRelativePath+o.lastModified+o.name+o.size}),N===!0){const o=P.map(d=>d.__key);a=C(a,p,"duplicate",d=>o.includes(d.__key)===!1)}if(a.length===0)return h();if(l.maxTotalSize!==void 0){let o=N===!0?P.reduce((d,q)=>d+q.size,0):0;if(a=C(a,p,"max-total-size",d=>(o+=d.size,o<=z.value)),a.length===0)return h()}if(typeof l.filter=="function"){const o=l.filter(a);a=C(a,p,"filter",d=>o.includes(d))}if(l.maxFiles!==void 0){let o=N===!0?P.length:0;if(a=C(a,p,"max-files",()=>(o++,o<=k.value)),a.length===0)return h()}if(h(),a.length!==0)return a}function O(t){A(t),n.value!==!0&&(n.value=!0)}function r(t){G(t),(t.relatedTarget!==null||me.is.safari!==!0?t.relatedTarget!==y.value:document.elementsFromPoint(t.clientX,t.clientY).includes(y.value)===!1)===!0&&(n.value=!1)}function _(t){A(t);const v=t.dataTransfer.files;v.length!==0&&b(null,v),n.value=!1}function T(t){if(n.value===!0)return V("div",{ref:y,class:`q-${t}__dnd absolute-full`,onDragenter:A,onDragover:A,onDragleave:r,onDrop:_})}return Object.assign(u,{pickFiles:U,addFiles:D}),{pickFiles:U,addFiles:D,onDragover:O,onDragleave:r,processFiles:Q,getDndNode:T,maxFilesNumber:k,maxTotalSizeNumber:z}}var Se=he({name:"QFile",inheritAttrs:!1,props:{...$,...ee,...Fe,modelValue:[File,FileList,Array],append:Boolean,useChips:Boolean,displayValue:[String,Number],tabindex:{type:[String,Number],default:0},counterLabel:Function,inputClass:[Array,String,Object],inputStyle:[Array,String,Object]},emits:[...te,...ye],setup(e,{slots:n,emit:F,attrs:b}){const{proxy:l}=X(),s=le(),u=B(null),y=B(!1),w=ne(e),{pickFiles:k,onDragover:z,onDragleave:U,processFiles:D,getDndNode:Q}=xe({editable:s.editable,dnd:y,getFileInput:R,addFilesToQueue:K}),O=ie(e),r=f(()=>Object(e.modelValue)===e.modelValue?"length"in e.modelValue?Array.from(e.modelValue):[e.modelValue]:[]),_=f(()=>L(r.value)),T=f(()=>r.value.map(i=>i.name).join(", ")),t=f(()=>oe(r.value.reduce((i,m)=>i+m.size,0))),v=f(()=>({totalSize:t.value,filesNumber:r.value.length,maxFiles:e.maxFiles})),P=f(()=>({tabindex:-1,type:"file",title:"",accept:e.accept,capture:e.capture,name:w.value,...b,id:s.targetUid.value,disabled:s.editable.value!==!0})),N=f(()=>"q-file q-field--auto-height"+(y.value===!0?" q-file--dnd":"")),a=f(()=>e.multiple===!0&&e.append===!0);function p(i){const m=r.value.slice();m.splice(i,1),o(m)}function h(i){const m=r.value.indexOf(i);m!==-1&&p(m)}function o(i){F("update:modelValue",e.multiple===!0?i:i[0])}function d(i){i.keyCode===13&&pe(i)}function q(i){(i.keyCode===13||i.keyCode===32)&&k(i)}function R(){return u.value}function K(i,m){const g=D(i,m,r.value,a.value),E=R();E!=null&&(E.value=""),g!==void 0&&((e.multiple===!0?e.modelValue&&g.every(Z=>r.value.includes(Z)):e.modelValue===g[0])||o(a.value===!0?r.value.concat(g):g))}function j(){return[V("input",{class:[e.inputClass,"q-file__filler"],style:e.inputStyle})]}function H(){if(n.file!==void 0)return r.value.length===0?j():r.value.map((m,g)=>n.file({index:g,file:m,ref:this}));if(n.selected!==void 0)return r.value.length===0?j():n.selected({files:r.value,ref:this});if(e.useChips===!0)return r.value.length===0?j():r.value.map((m,g)=>V(re,{key:"file-"+g,removable:s.editable.value,dense:!0,textColor:e.color,tabindex:e.tabindex,onRemove:()=>{p(g)}},()=>V("span",{class:"ellipsis",textContent:m.name})));const i=e.displayValue!==void 0?e.displayValue:T.value;return i.length!==0?[V("div",{class:e.inputClass,style:e.inputStyle,textContent:i})]:j()}function J(){const i={ref:u,...P.value,...O.value,class:"q-field__input fit absolute-full cursor-pointer",onChange:K};return e.multiple===!0&&(i.multiple=!0),V("input",i)}return Object.assign(s,{fieldClass:N,emitValue:o,hasValue:_,inputRef:u,innerValue:r,floatingLabel:f(()=>_.value===!0||L(e.displayValue)),computedCounter:f(()=>{if(e.counterLabel!==void 0)return e.counterLabel(v.value);const i=e.maxFiles;return`${r.value.length}${i!==void 0?" / "+i:""} (${t.value})`}),getControlChild:()=>Q("file"),getControl:()=>{const i={ref:s.targetRef,class:"q-field__native row items-center cursor-pointer",tabindex:e.tabindex};return s.editable.value===!0&&Object.assign(i,{onDragover:z,onDragleave:U,onKeydown:d,onKeyup:q}),V("div",i,[J()].concat(H()))}}),Object.assign(l,{removeAtIndex:p,removeFile:h,getNativeElement:()=>u.value}),fe(l,"nativeEl",()=>u.value),ae(s)}});const Ce={data(){return{name:"",email:"",password:"",confirmpassword:"",introduction:"",phoneNumber:"",role:null,profilePicture:"",roleOptions:[{label:"\uC77C\uBC18 \uC0AC\uC6A9\uC790",value:"USER"},{label:"\uAD00\uB9AC\uC790",value:"ADMIN"}]}},methods:{signup(){if(!this.name||!this.email||!this.password||!this.confirmpassword){console.error("\uD544\uC218 \uC815\uBCF4\uB97C \uC785\uB825\uD558\uC138\uC694.");return}const e=new FormData;e.append("name",this.name),e.append("email",this.email),e.append("password",this.password),e.append("confirmpassword",this.confirmpassword),e.append("introduction",this.introduction),e.append("tlno",this.phoneNumber),e.append("role",this.role?this.role.value:""),this.profilePicture!==""&&e.append("profilePicUrl",this.profilePicture),se.post("http://localhost:8080/api/v1/users/signup",e,{headers:{"Content-Type":"multipart/form-data"}}).then(n=>{console.log("Signup successful",n.data),alert("\uD68C\uC6D0\uAC00\uC785\uC774 \uC644\uB8CC\uB418\uC5C8\uC2B5\uB2C8\uB2E4."),this.$router.push("/login")}).catch(n=>{console.error("Signup failed",n.response.data)})},cancelImageUpload(){this.profilePicture=""}}},Pe=ge("div",{class:"text-h6"},"\uD68C\uC6D0\uAC00\uC785",-1);function Ne(e,n,F,b,l,s){return M(),W(be,{class:"flex flex-center"},{default:S(()=>[c(ue,{class:"q-pa-md",style:{width:"400px"}},{default:S(()=>[c(I,null,{default:S(()=>[Pe]),_:1}),c(I,null,{default:S(()=>[c(x,{outlined:"",modelValue:l.name,"onUpdate:modelValue":n[0]||(n[0]=u=>l.name=u),label:"\uC774\uB984"},null,8,["modelValue"]),c(x,{outlined:"",modelValue:l.email,"onUpdate:modelValue":n[1]||(n[1]=u=>l.email=u),label:"\uC774\uBA54\uC77C"},null,8,["modelValue"]),c(x,{outlined:"",type:"password",modelValue:l.password,"onUpdate:modelValue":n[2]||(n[2]=u=>l.password=u),label:"\uBE44\uBC00\uBC88\uD638"},null,8,["modelValue"]),c(x,{outlined:"",type:"password",modelValue:l.confirmpassword,"onUpdate:modelValue":n[3]||(n[3]=u=>l.confirmpassword=u),label:"\uBE44\uBC00\uBC88\uD638 \uD655\uC778"},null,8,["modelValue"]),c(x,{outlined:"",modelValue:l.introduction,"onUpdate:modelValue":n[4]||(n[4]=u=>l.introduction=u),label:"\uC790\uAE30\uC18C\uAC1C"},null,8,["modelValue"]),c(x,{outlined:"",modelValue:l.phoneNumber,"onUpdate:modelValue":n[5]||(n[5]=u=>l.phoneNumber=u),label:"\uD734\uB300\uD3F0 \uBC88\uD638"},null,8,["modelValue"]),c(de,{outlined:"",modelValue:l.role,"onUpdate:modelValue":n[6]||(n[6]=u=>l.role=u),label:"\uC5ED\uD560",options:l.roleOptions},null,8,["modelValue","options"])]),_:1}),c(I,{class:"q-pt-sm"},{default:S(()=>[c(Se,{modelValue:l.profilePicture,"onUpdate:modelValue":n[7]||(n[7]=u=>l.profilePicture=u),label:"\uD504\uB85C\uD544 \uC0AC\uC9C4",accept:"image/*"},null,8,["modelValue"]),l.profilePicture!==""?(M(),W(Y,{key:0,label:"\uC774\uBBF8\uC9C0 \uCDE8\uC18C",color:"negative",onClick:s.cancelImageUpload},null,8,["onClick"])):ve("",!0)]),_:1}),c(I,{class:"text-right"},{default:S(()=>[c(Y,{label:"\uAC00\uC785\uD558\uAE30",color:"primary",onClick:s.signup},null,8,["onClick"])]),_:1})]),_:1})]),_:1})}var Te=Ve(Ce,[["render",Ne]]);export{Te as default};
